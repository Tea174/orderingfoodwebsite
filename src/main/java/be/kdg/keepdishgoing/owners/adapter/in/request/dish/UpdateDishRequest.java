package be.kdg.keepdishgoing.owners.adapter.in.request.dish;

import be.kdg.keepdishgoing.owners.domain.DishType;
import jakarta.validation.constraints.*;
import java.util.List;

public record UpdateDishRequest(
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