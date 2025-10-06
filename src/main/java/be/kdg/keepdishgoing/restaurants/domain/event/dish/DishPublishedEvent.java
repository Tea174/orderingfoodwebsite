package be.kdg.keepdishgoing.restaurants.domain.event.dish;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishPublishedEvent(LocalDateTime eventPit, UUID restaurantId, UUID dishId) implements DishEvent {

    public DishPublishedEvent (LocalDateTime eventPit, UUID restaurantId, UUID dishId) {
        this.eventPit = eventPit;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }
    public DishPublishedEvent(UUID restaurantId, UUID dishId){
        this(LocalDateTime.now(), restaurantId, dishId);
    }

    @Override
    public LocalDateTime occurredAt() {
        return null;
    }
}
