package be.kdg.keepdishgoing.orders.adapter.in.basket.request;

import java.util.UUID;

public record AddItemRequest(UUID customerId,
                             UUID restaurantId,
                             UUID dishId,
                             int quantity) {
}
