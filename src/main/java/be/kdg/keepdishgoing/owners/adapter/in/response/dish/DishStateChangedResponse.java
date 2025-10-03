package be.kdg.keepdishgoing.owners.adapter.in.response.dish;

import be.kdg.keepdishgoing.owners.domain.DishState;

public record DishStateChangedResponse(
        DishState newState,
        String message,
        boolean success
) {
    public static DishStateChangedResponse published() {
        return new DishStateChangedResponse(
                DishState.PUBLISHED,
                "Dish published successfully",
                true
        );
    }

    public static DishStateChangedResponse unpublished() {
        return new DishStateChangedResponse(
                DishState.UNPUBLISHED,
                "Dish unpublished successfully",
                true
        );
    }

    public static DishStateChangedResponse outOfStock() {
        return new DishStateChangedResponse(
                DishState.OUTOFSTOCK,
                "Dish marked as out of stock",
                true
        );
    }
}