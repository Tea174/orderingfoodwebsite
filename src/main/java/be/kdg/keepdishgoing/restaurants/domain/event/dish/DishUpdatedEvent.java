package be.kdg.keepdishgoing.restaurants.domain.event.dish;

import java.time.LocalDateTime;

public record DishUpdatedEvent() implements DishEvent {
    @Override
    public LocalDateTime occurredAt() {
        return null;
    }
}
