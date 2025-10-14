package be.kdg.keepdishgoing.restaurants.adapter.in.response.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;

import java.sql.Time;

public record RestaurantByTypeOfCuisineResponse(
        TypeOfCuisine cuisine,
        Double minPrice,
        Double maxPrice,
        Time estimatedDeliveryTime
) {
    public static RestaurantByTypeOfCuisineResponse fromDomain(Restaurant restaurant) {
        return new RestaurantByTypeOfCuisineResponse(
                restaurant.getCuisine(),
                restaurant.getMinPrice(),
                restaurant.getMaxPrice(),
                restaurant.getEstimatedDeliveryTime()
        );
    }
}