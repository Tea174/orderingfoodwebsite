package be.kdg.keepdishgoing.owners.adapter.in.response.dish;

public record DishUpdatedResponse(
        String message,
        boolean success
) {
    public static DishUpdatedResponse success() {
        return new DishUpdatedResponse("Dish updated successfully", true);
    }
}