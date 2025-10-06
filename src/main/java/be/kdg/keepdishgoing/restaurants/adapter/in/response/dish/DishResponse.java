package be.kdg.keepdishgoing.restaurants.adapter.in.response.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishState;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record DishResponse(
        UUID dishId,
        UUID restaurantId,
        String name,
        DishType dishType,
        List<String> foodTags,
        String description,
        double price,
        String pictureURL,
        DishState state
) {
    public static DishResponse fromDomain(Dish dish) {
        return new DishResponse(
                dish.getDishId().id(),
                dish.getRestaurantId().id(),
                dish.getName(),
                dish.getDishType(),
                dish.getFoodTags() != null ?
                        dish.getFoodTags().stream()
                                .map(FoodTag::value)
                                .collect(Collectors.toList()) :
                        List.of(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureURL(),
                dish.getState()
        );
    }
}