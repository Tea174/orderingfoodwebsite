package be.kdg.keepdishgoing.restaurants.adapter.in.request.restautant;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.List;

public record CreateRestaurantRequest(
        @NotBlank(message = "RestaurantProjectorRecord name is required")
        String name,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank @Email(message = "Valid email is required")
        String email,

        String pictureURL,

        @NotNull(message = "Cuisine type is required")
        TypeOfCuisine cuisine,

        @NotNull(message = "Preparation time is required")
        Integer preparationTime,

        @NotNull(message = "Opening time is required")
        Time openingTime,

        Double minPrice,
        Double maxPrice,
        Time estimatedDeliveryTime,


        @Valid
        List<DishRequest> dishes
) {
    public record DishRequest(
            @NotBlank(message = "DishProjectorRecord name is required")
            String name,

            @NotNull(message = "DishProjectorRecord type is required")
            DishType dishType,

            List<FoodTag> foodTags,

            @NotBlank(message = "Description is required")
            String description,

            @NotNull @DecimalMin(value = "0.0", message = "Price must be positive")
            Double price,

            String pictureURL
    ) {}
}