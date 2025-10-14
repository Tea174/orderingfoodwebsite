package be.kdg.keepdishgoing.common.event.customerEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerCreatedEvent(
        LocalDateTime eventPit,
        UUID customerId,
        String keycloakId
) implements DomainEvent {}