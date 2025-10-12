package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

public record DishPublishedResponse(
        String dishId,
        String name,
        Double price,
        boolean inStock
) {}