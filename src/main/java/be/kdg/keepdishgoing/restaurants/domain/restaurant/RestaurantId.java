package be.kdg.keepdishgoing.restaurants.domain.restaurant;

import java.util.UUID;

public record RestaurantId (UUID id) {

    public static RestaurantId of(UUID id) {
        return new RestaurantId(id);
    }

    public static RestaurantId create() {
        return new RestaurantId(UUID.randomUUID());
    }

}
