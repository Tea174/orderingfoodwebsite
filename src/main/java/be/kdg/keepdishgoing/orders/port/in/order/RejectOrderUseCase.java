package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface RejectOrderUseCase {
    void rejectOrder(UUID orderId, String reason);
}