package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

import java.util.UUID;

public record DishCreatedResponse(
        UUID dishId,
        String message,
        boolean success
) {
    public static DishCreatedResponse of(UUID dishId) {
        return new DishCreatedResponse(
                dishId,
                "DishProjectorRecord created successfully",
                true
        );
    }
}