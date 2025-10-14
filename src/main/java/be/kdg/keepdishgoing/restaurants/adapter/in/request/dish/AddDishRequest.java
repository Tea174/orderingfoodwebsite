package be.kdg.keepdishgoing.restaurants.adapter.in.request.dish;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;

import java.util.List;
import java.util.UUID;

public record AddDishRequest(
        @NotNull(message = "RestaurantProjectorRecord ID is required")
        UUID restaurantId,

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