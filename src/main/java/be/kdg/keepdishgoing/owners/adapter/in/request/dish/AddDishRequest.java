package be.kdg.keepdishgoing.owners.adapter.in.request.dish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import be.kdg.keepdishgoing.owners.domain.dish.DishType;

import java.util.List;
import java.util.UUID;

public record AddDishRequest(
        @NotNull(message = "Restaurant ID is required")
        UUID restaurantId,

        @NotBlank(message = "Dish name is required")
        @Size(min = 2, max = 100, message = "Dish name must be between 2 and 100 characters")
        String name,

        @NotNull(message = "Dish type is required")
        DishType dishType,

        List<String> foodTags,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        @Positive(message = "Price must be positive")
        double price,

        String pictureURL
) {}