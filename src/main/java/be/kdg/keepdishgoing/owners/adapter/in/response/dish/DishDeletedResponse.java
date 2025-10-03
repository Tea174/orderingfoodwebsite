package be.kdg.keepdishgoing.owners.adapter.in.response.dish;

public record DishDeletedResponse(
        String message,
        boolean success
) {
    public static DishDeletedResponse success() {
        return new DishDeletedResponse("Dish deleted successfully", true);
    }
}