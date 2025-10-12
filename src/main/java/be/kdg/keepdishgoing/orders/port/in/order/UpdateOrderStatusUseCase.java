package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.domain.order.OrderStatus;

public interface UpdateOrderStatusUseCase {
    void updateStatus(OrderId orderId, OrderStatus newStatus);
}
