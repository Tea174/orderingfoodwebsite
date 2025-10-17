package be.kdg.keepdishgoing.restaurants.adapter.in.response.owner;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderActionResponse(
        UUID orderId,
        UUID restaurantId,
        String action,  // "ACCEPTED" or "REJECTED"
        String message,
        LocalDateTime timestamp
) {
    public static OrderActionResponse accepted(UUID orderId, UUID restaurantId) {
        return new OrderActionResponse(
                orderId,
                restaurantId,
                "ACCEPTED",
                "Order has been accepted and forwarded to delivery",
                LocalDateTime.now()
        );
    }

    public static OrderActionResponse rejected(UUID orderId, UUID restaurantId, String reason) {
        return new OrderActionResponse(
                orderId,
                restaurantId,
                "REJECTED",
                "Order has been rejected: " + reason,
                LocalDateTime.now()
        );
    }
}