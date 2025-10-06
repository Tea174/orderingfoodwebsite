package be.kdg.keepdishgoing.restaurants.port.in.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

import java.util.List;

public interface GetDishesByRestaurantUseCase {
    List<Dish> getDishesByRestaurant(RestaurantId restaurantId);
}