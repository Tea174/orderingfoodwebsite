package be.kdg.keepdishgoing.owners.port.in.dish;

import be.kdg.keepdishgoing.owners.domain.dish.DishId;
import be.kdg.keepdishgoing.owners.domain.dish.DishType;
import be.kdg.keepdishgoing.owners.domain.dish.FoodTag;
import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;

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