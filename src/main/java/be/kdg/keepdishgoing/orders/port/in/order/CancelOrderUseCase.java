package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

public interface CancelOrderUseCase {
    void cancel(OrderId orderId);
}
