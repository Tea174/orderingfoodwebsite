package be.kdg.keepdishgoing.orders.port.in.basket;

import java.util.UUID;

public interface AddItemToBasketUseCase {
    void addItem(UUID customerId, UUID restaurantId, UUID dishId, int quantity);
    void addItemToGuestBasket(UUID basketId, UUID restaurantId, UUID dishId, int quantity);
}