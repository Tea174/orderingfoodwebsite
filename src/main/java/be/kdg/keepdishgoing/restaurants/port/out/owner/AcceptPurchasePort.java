package be.kdg.keepdishgoing.restaurants.port.out.owner;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface AcceptPurchasePort {
    void acceptOrder(UUID restaurantId, OrderId orderId);
}
