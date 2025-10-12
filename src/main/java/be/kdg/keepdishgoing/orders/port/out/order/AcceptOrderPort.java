package be.kdg.keepdishgoing.orders.port.out.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface AcceptOrderPort {
    void acceptOrder(UUID restaurantId, OrderId orderId);
}
