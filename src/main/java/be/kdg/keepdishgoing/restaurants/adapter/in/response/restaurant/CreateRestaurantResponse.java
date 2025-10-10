package be.kdg.keepdishgoing.restaurants.adapter.in.response.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.TypeOfCuisine;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public record CreateRestaurantResponse(
        UUID restaurantId,
        UUID ownerId,
        String name,
        String address,
        String email,
        String pictureURL,
        TypeOfCuisine cuisine,
        Time preparationTime,
        Time openingTime,
        Double minPrice,
        Double maxPrice,
        Integer estimatedDeliveryTime,
        List<DishResponse> dishes
) {
    public static CreateRestaurantResponse fromDomain(Restaurant restaurant) {
        return new CreateRestaurantResponse(
                restaurant.getRestaurantId().id(),
                restaurant.getOwnerId().id(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getEmail(),
                restaurant.getPictureURL(),
                restaurant.getCuisine(),
                restaurant.getPreparationTime(),
                restaurant.getOpeningTime(),
                restaurant.getMinPrice(),
                restaurant.getMaxPrice(),
                restaurant.getEstimatedDeliveryTime(),
                restaurant.getDishes() != null
                        ? restaurant.getDishes().stream()
                        .map(DishResponse::fromDomain)
                        .toList()
                        : List.of()
        );
    }

    public record DishResponse(
            UUID dishId,
            String name,
            DishType dishType,
            List<FoodTag> foodTags,
            String description,
            Double price,
            String pictureURL
    ) {
        public static DishResponse fromDomain(Dish dish) {
            return new DishResponse(
                    dish.getDishId().id(),
                    dish.getName(),
                    dish.getDishType(),
                    dish.getFoodTags(),
                    dish.getDescription(),
                    dish.getPrice(),
                    dish.getPictureURL()
            );
        }
    }
}