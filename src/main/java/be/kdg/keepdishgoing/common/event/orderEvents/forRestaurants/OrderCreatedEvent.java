package be.kdg.keepdishgoing.common.event.orderEvents.forRestaurants;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID restaurantId
) implements DomainEvent {
}
