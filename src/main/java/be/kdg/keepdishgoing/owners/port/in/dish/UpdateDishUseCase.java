package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.DishId;
import be.kdg.keepdishgoing.owners.domain.DishType;
import be.kdg.keepdishgoing.owners.domain.FoodTag;

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