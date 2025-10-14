package be.kdg.keepdishgoing.restaurants.port.in.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

import java.util.List;

public interface AddDishUseCase {
    DishId addDish(AddDishCommand command);

    record AddDishCommand(
            RestaurantId restaurantId,
            String name,
            DishType dishType,
            List<FoodTag> foodTags,
            String description,
            double price,
            String pictureURL
    ) {}
}