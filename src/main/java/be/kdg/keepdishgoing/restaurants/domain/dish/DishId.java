package be.kdg.keepdishgoing.restaurants.domain.dish;

import java.util.UUID;

public record DishId(UUID id) {
    public static DishId create() {
        return new DishId(UUID.randomUUID());
    }

    public static DishId of(UUID id) {
        return new DishId(id);
    }
}
