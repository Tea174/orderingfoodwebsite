package be.kdg.keepdishgoing.common.event.customerEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerCreatedEvent(
        LocalDateTime eventPit,
        UUID customerId,
        String keycloakId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address
) implements DomainEvent {}