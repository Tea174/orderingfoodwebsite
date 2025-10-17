package be.kdg.keepdishgoing.common.event.restaurantEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestaurantRejectedOrderEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID restaurantId,
        String rejectionReason
) implements DomainEvent {
}