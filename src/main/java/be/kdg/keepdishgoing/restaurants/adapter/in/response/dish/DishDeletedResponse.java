package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

public record DishDeletedResponse(
        String message,
        boolean success
) {
    public static DishDeletedResponse deletedSuccess() {
        return new DishDeletedResponse("Dish deleted successfully", true);
    }
}