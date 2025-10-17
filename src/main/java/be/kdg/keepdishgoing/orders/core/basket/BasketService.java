package be.kdg.keepdishgoing.orders.core.basket;

import be.kdg.keepdishgoing.orders.domain.basket.Basket;
import be.kdg.keepdishgoing.orders.domain.basket.BasketItem;
import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.port.in.basket.*;
import be.kdg.keepdishgoing.orders.port.out.basket.*;
import be.kdg.keepdishgoing.orders.port.out.order.PublishOrderEventsPort;
import be.kdg.keepdishgoing.orders.port.out.order.SaveOrderPort;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.port.in.dish.GetDishUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.dish.GetDishUseCase.PublishedDishDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class BasketService implements
        AddItemToBasketUseCase,
        ValidateBasketUseCase,
        CheckoutBasketUseCase,
        CreateGuestBasketUseCase{

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;
    private final SaveOrderPort saveOrderPort;
    private final GetDishUseCase getDishUseCase;
    private final PublishOrderEventsPort publishOrderEventsPort;

    @Override
    public OrderId checkoutAsGuest(UUID basketId, GuestCheckoutDetails guestDetails) {
        // 1. Load basket by ID (not customerId)
        Basket basket = loadBasketPort.loadById(basketId)
                .orElseThrow(() -> new IllegalStateException("Basket not found"));

        // 2. Validate basket items are still available
        ValidationResult validation = validateBasketItems(basket);
        if (!validation.valid()) {
            throw new IllegalStateException("Cannot checkout: " + validation.message());
        }
        Order order = Order.fromBasketWithGuestDetails(
                basket,
                guestDetails.name(),
                guestDetails.email(),
                guestDetails.deliveryAddress()
        );
        saveOrderPort.saveOrder(order);
        saveBasketPort.delete(basket);
        publishOrderEventsPort.publishEvents(order);
        return order.getOrderId();
    }


    private ValidationResult validateBasketItems(Basket basket) {
        if (basket.isEmpty()) {
            return new ValidationResult(false, "Basket is empty", List.of());
        }
        List<InvalidItem> invalidItems = new ArrayList<>();
        for (BasketItem item : basket.getItems()) {
            try {
                PublishedDishDto dish = getDishUseCase.getPublishedDish(new DishId(item.getDishId()));

                if (!dish.inStock()) {
                    invalidItems.add(new InvalidItem(
                            item.getDishId(),
                            item.getDishName(),
                            "OUT_OF_STOCK"
                    ));
                }
            } catch (Exception e) {
                invalidItems.add(new InvalidItem(
                        item.getDishId(),
                        item.getDishName(),
                        "UNPUBLISHED"
                ));
            }
        }
        if (invalidItems.isEmpty()) {
            return new ValidationResult(true, "Basket is valid", List.of());
        } else {
            return new ValidationResult(false, "Some items are no longer available", invalidItems);
        }
    }

    @Override
    public ValidationResult validateBasket(UUID customerId) {
        Basket basket = loadBasketPort.loadByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("Basket not found"));
        if (basket.isEmpty()) {
            return new ValidationResult(false, "Basket is empty", List.of());
        }
        List<InvalidItem> invalidItems = new ArrayList<>();
        for (BasketItem item : basket.getItems()) {
            try {
                PublishedDishDto dish = getDishUseCase.getPublishedDish(new DishId(item.getDishId()));
                if (!dish.inStock()) {
                    invalidItems.add(new InvalidItem(
                            item.getDishId(),
                            item.getDishName(),
                            "OUT_OF_STOCK"
                    ));
                }
            } catch (Exception e) {
                invalidItems.add(new InvalidItem(
                        item.getDishId(),
                        item.getDishName(),
                        "UNPUBLISHED"
                ));
            }
        }
        if (invalidItems.isEmpty()) {
            return new ValidationResult(true, "Basket is valid", List.of());
        } else {
            return new ValidationResult(
                    false,
                    "Some items are no longer available",
                    invalidItems
            );
        }
    }

    @Override
    public OrderId checkout(UUID customerId) {
        Basket basket = loadBasketPort.loadByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("Basket not found"));

        ValidationResult validation = validateBasket(customerId);
        if (!validation.valid()) {
            throw new IllegalStateException(
                    "Cannot checkout: " + validation.message()
            );
        }
        Order order = Order.fromBasket(basket);
        saveOrderPort.saveOrder(order);
        saveBasketPort.delete(basket);
        publishOrderEventsPort.publishEvents(order);
        return order.getOrderId();
    }

    @Override
    public void addItem(UUID customerId, UUID restaurantId, UUID dishId, int quantity) {
        // 1. Get dish details from RestaurantProjectorRecord BC
        PublishedDishDto dish = getDishUseCase.getPublishedDish(new DishId(dishId));
        if (!dish.inStock()) {
            throw new IllegalStateException("DishProjectorRecord is out of stock");
        }
        if (!dish.restaurantId().id().equals(restaurantId)) {
            throw new IllegalArgumentException("DishProjectorRecord does not belong to this restaurant");
        }
        Basket basket = loadBasketPort.loadByCustomerId(customerId)
                .orElse(Basket.createBasket(customerId, restaurantId, new ArrayList<>()));
        basket.validateSingleRestaurant(restaurantId);
        basket.addItem(dishId, dish.name(), dish.price(), quantity);
        saveBasketPort.save(basket);
    }

    @Override
    public void addItemToGuestBasket(UUID basketId, UUID restaurantId, UUID dishId, int quantity) {
        Basket basket = loadBasketPort.loadById(basketId)
                .orElseThrow(() -> new IllegalStateException("Basket not found"));
        PublishedDishDto dish = getDishUseCase.getPublishedDish(new DishId(dishId));
        basket.addItem(dishId, dish.name(), dish.price(), quantity);
        basket.setRestaurantId(restaurantId);
        saveBasketPort.save(basket);
    }

    @Override
    public UUID create() {
        Basket guestBasket = Basket.createGuestBasket(null); ; // customerId = null for guest
        saveBasketPort.save(guestBasket);
        return guestBasket.getBasketId().id();
    }
}