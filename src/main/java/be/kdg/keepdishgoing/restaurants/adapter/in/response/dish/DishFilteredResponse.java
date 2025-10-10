package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishState;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;

import java.util.List;

public record DishFilteredResponse(
        String restaurantName,
        String name,
        DishType dishType,
        List<FoodTag> foodTags,
        String description,
        double price,
        String pictureURL,
        DishState state
) {
    public static DishFilteredResponse fromDomain(Dish dish, String restaurantName) {
        return new DishFilteredResponse(
                restaurantName,
                dish.getName(),
                dish.getDishType(),
                dish.getFoodTags() != null ? dish.getFoodTags() : List.of(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureURL(),
                dish.getState()
        );
    }
}