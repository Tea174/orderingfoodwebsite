package be.kdg.keepdishgoing.common.event.orderEvents.forDelivery;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderAcceptedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID customerId,
        UUID restaurantId,
        String guestName,
        String guestEmail,           // null for customer orders
        String recipientName,        // Who receives delivery (always present)
        Integer recipientPhone,       // Contact number (always present)
        String deliveryAddress   // Where to deliver (always present)

) implements DomainEvent {

    public boolean isGuestOrder() {
        return customerId == null;
    }
}