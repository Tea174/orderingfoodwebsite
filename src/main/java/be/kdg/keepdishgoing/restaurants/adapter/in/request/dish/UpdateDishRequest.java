package be.kdg.keepdishgoing.restaurants.adapter.in.request.dish;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import jakarta.validation.constraints.*;
import java.util.List;

public record UpdateDishRequest(
        @NotBlank(message = "DishProjectorRecord name is required")
        @Size(min = 2, max = 100, message = "DishProjectorRecord name must be between 2 and 100 characters")
        String name,

        @NotNull(message = "DishProjectorRecord type is required")
        DishType dishType,

        List<FoodTag> foodTags,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @Positive(message = "Price must be positive")
        double price,

        String pictureURL
) {}