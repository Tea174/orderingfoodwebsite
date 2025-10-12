package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

public record DishStockChangedResponse(
        boolean inStock,
        String message,
        boolean success
) {
    public static DishStockChangedResponse outOfStock() {
        return new DishStockChangedResponse(
                false,
                "Dish marked as out of stock",
                true
        );
    }

    public static DishStockChangedResponse backInStock() {
        return new DishStockChangedResponse(
                true,
                "Dish marked as in stock",
                true
        );
    }
}