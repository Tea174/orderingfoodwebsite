package be.kdg.keepdishgoing.orders.domain.basket;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Basket {
    private BasketId basketId;
    private UUID customerId; // null for guest
    private UUID restaurantId;
    private List<BasketItem> items;

    // Delivery details - should be in Basket
    private String deliveryAddress;

    // For registered customers (fetched from customer profile or specified)
    private String customerName;
    private Integer customerPhone;


    // Add item to basket
    public void addItem(UUID dishId, String dishName, Double price, int quantity) {
        // Check if item already exists, update quantity
        Optional<BasketItem> existing = items.stream()
                .filter(item -> item.getDishId().equals(dishId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().increaseQuantity(quantity);
        } else {
            items.add(new BasketItem(dishId, dishName, price, quantity));
        }
    }
    public static Basket createGuestBasket(UUID customerId) {
        Basket basket = new Basket();
        basket.setCustomerId(null);
        basket.setBasketId(BasketId.create());
        return basket;
    }

    public Basket(UUID customerId, BasketId basketId) {
        this.customerId = null;
        this.basketId = basketId;
    }

    public void removeItem(UUID dishId) {
        items.removeIf(item -> item.getDishId().equals(dishId));
    }

    public void updateItemQuantity(UUID dishId, int newQuantity) {
        if (newQuantity <= 0) {
            removeItem(dishId);
            return;
        }

        items.stream()
                .filter(item -> item.getDishId().equals(dishId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(newQuantity));
    }

    public void clear() {
        items.clear();
    }

    public Double calculateTotal() {
        return items.stream()
                .mapToDouble(BasketItem::getSubtotal)
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isValidForCheckout() {
        return !isEmpty() && restaurantId != null;
    }

    public int getItemCount() {
        return items.stream()
                .mapToInt(BasketItem::getQuantity)
                .sum();
    }

    // Validate all items belong to same restaurant
    public void validateSingleRestaurant(UUID newDishRestaurantId) {
        if (restaurantId != null && !restaurantId.equals(newDishRestaurantId)) {
            throw new IllegalStateException("Cannot add dishes from different restaurants");
        }
        if (restaurantId == null) {
            this.restaurantId = newDishRestaurantId;
        }
    }

    public static Basket createBasket(UUID customerId, UUID restaurantId, List<BasketItem> items) {
        return new Basket(BasketId.create(), customerId, restaurantId, items);
    }

    public Basket(BasketId basketId, UUID customerId, UUID restaurantId, List<BasketItem> items) {
        this.basketId = basketId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    public Basket() {
    }
}