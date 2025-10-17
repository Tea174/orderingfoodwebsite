package be.kdg.keepdishgoing.common.event.orderEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID customerId,          // null for guest orders
        UUID restaurantId,
        String guestName,         // null for registered customers
        String guestEmail,        // null for registered customers
        String deliveryAddress,   // null for registered customers
        Double totalPrice
) implements DomainEvent {
}
