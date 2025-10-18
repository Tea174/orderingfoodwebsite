package be.kdg.keepdishgoing.common.event.orderEvents.forRestaurants;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        LocalDateTime eventPit,
        UUID orderId,       // null for guest orders
        UUID restaurantId
) implements DomainEvent {
}
