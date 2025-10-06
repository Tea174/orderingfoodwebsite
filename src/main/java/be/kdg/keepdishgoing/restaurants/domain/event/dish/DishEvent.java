package be.kdg.keepdishgoing.restaurants.domain.event.dish;

import java.time.LocalDateTime;

public interface DishEvent {
    LocalDateTime occurredAt();

}
