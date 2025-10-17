package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface AcceptOrderUseCase {
    void acceptOrder(UUID orderId);

}
