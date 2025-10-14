package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishState;

public record DishStateChangedResponse(
        DishState newState,
        String message,
        boolean success
) {
    public static DishStateChangedResponse published() {
        return new DishStateChangedResponse(
                DishState.PUBLISHED,
                "DishProjectorRecord published successfully",
                true
        );
    }

    public static DishStateChangedResponse unpublished() {
        return new DishStateChangedResponse(
                DishState.UNPUBLISHED,
                "DishProjectorRecord unpublished successfully",
                true
        );
    }

}