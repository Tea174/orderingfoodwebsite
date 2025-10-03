package be.kdg.keepdishgoing.owners.domain.event.dish;


import java.time.LocalDateTime;
import java.util.UUID;

public record DishAddedEvent(LocalDateTime eventPit, UUID restaurantId,UUID dishId) implements DishEvent {

    public DishAddedEvent(LocalDateTime eventPit, UUID restaurantId, UUID dishId) {
        this.eventPit = eventPit;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }

    public DishAddedEvent(UUID restaurantId, UUID dishId) {
        this(LocalDateTime.now(), restaurantId, dishId);
    }



    @Override
    public LocalDateTime occurredAt() {
        return null;
    }

}
