package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

public record DishStockChangedResponse(
        boolean inStock,
        String message,
        boolean success
) {
    public static DishStockChangedResponse outOfStock() {
        return new DishStockChangedResponse(
                false,
                "DishProjectorRecord marked as out of stock",
                true
        );
    }

    public static DishStockChangedResponse backInStock() {
        return new DishStockChangedResponse(
                true,
                "DishProjectorRecord marked as in stock",
                true
        );
    }
}