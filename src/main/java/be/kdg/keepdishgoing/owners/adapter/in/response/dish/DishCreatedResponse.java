package be.kdg.keepdishgoing.owners.adapter.in.response.dish;

import java.util.UUID;

public record DishCreatedResponse(
        UUID dishId,
        String message,
        boolean success
) {
    public static DishCreatedResponse of(UUID dishId) {
        return new DishCreatedResponse(
                dishId,
                "Dish created successfully",
                true
        );
    }
}