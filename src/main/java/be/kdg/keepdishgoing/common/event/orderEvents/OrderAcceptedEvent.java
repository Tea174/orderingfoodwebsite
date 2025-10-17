package be.kdg.keepdishgoing.common.event.orderEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;


public record OrderAcceptedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID customerId,  // null for guest
        UUID restaurantId,
        String recipientName,
        String recipientPhone,
        String deliveryAddress,
        String deliveryInstructions
) implements DomainEvent {

    public boolean isGuestOrder() {
        return customerId == null;
    }
}