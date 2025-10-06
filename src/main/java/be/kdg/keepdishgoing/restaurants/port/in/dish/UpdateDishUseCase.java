package be.kdg.keepdishgoing.restaurants.port.in.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;

import java.util.List;

public interface UpdateDishUseCase {
    void updateDish(UpdateDishCommand command);

    record UpdateDishCommand(
            DishId dishId,
            String name,
            DishType dishType,
            List<FoodTag> foodTags,
            String description,
            double price,
            String pictureURL
    ) {}
}