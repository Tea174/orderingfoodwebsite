package be.kdg.keepdishgoing.common.event.orderEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderReadyForPickupEvent(
        LocalDateTime eventPit,
        UUID OrderId
) implements DomainEvent {


}