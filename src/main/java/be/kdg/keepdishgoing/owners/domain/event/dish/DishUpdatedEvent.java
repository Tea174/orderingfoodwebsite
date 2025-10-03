package be.kdg.keepdishgoing.owners.domain.event.dish;

import java.time.LocalDateTime;

public record DishUpdatedEvent() implements DishEvent {
    @Override
    public LocalDateTime occurredAt() {
        return null;
    }
}
