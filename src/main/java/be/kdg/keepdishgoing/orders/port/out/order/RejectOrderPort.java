package be.kdg.keepdishgoing.orders.port.out.order;

import java.util.UUID;

public interface RejectOrderPort {
    void rejectOrder(UUID restaurantId, UUID orderId, String reason);
}
