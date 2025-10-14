package be.kdg.keepdishgoing.common.event.dishEvents;

import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;


public record DishBackInStockEvent(
        LocalDateTime eventPit,
        UUID dishId,
        UUID restaurantId
) implements DomainEvent {}