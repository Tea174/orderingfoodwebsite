package be.kdg.keepdishgoing.common.event.orderEvents.forDelivery;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderAcceptedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID customerId,
        UUID restaurantId,
        int preparationTimeMinutes,
        String guestName,
        String guestEmail,           // null for customer orders
        String recipientName,        // Who receives delivery (always present)
        String recipientPhone,       // Contact number (always present)
        String deliveryAddress   // Where to deliver (always present)

) implements DomainEvent {

    public boolean isGuestOrder() {
        return customerId == null;
    }
}