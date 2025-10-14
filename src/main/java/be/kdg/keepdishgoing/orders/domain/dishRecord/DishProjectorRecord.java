package be.kdg.keepdishgoing.orders.domain.dishRecord;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;

import java.util.List;
import java.util.UUID;

public record DishProjectorRecord(
        UUID dishId,
        UUID restaurantId,
        String name,
        DishType dishType,
        List<FoodTag> foodTags,
        String description,
        double price,
        String pictureURL,
        boolean inStock
) {}