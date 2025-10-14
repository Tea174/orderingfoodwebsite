package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

public record DishUpdatedResponse(
        String message,
        boolean success
) {
    public static DishUpdatedResponse createSuccess() {  // More explicit
        return new DishUpdatedResponse("DishProjectorRecord updated successfully", true);
    }
}