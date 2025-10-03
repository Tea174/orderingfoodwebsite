package be.kdg.keepdishgoing.owners.domain.event.dish;

import be.kdg.keepdishgoing.owners.domain.DishId;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DishEvent {
    LocalDateTime occurredAt();

}
