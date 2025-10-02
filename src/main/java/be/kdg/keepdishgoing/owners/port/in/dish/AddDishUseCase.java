package be.kdg.keepdishgoing.owners.port.in.dish;

import be.kdg.keepdishgoing.owners.domain.*;

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