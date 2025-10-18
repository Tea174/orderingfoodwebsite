package be.kdg.keepdishgoing.restaurants.port.out.owner;

import java.util.UUID;

public interface RejectPurchasePort {
    void rejectOrder(UUID restaurantId, UUID orderId, String reason);
}
