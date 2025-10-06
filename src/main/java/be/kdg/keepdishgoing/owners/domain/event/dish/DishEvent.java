package be.kdg.keepdishgoing.owners.domain.event.dish;

import java.time.LocalDateTime;

public interface DishEvent {
    LocalDateTime occurredAt();

}
