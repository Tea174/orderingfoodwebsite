package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface LoadDishesPort {
    Optional<Dish> loadByDishId(DishId dishId);
    List<Dish> loadByRestaurantId(RestaurantId restaurantId);
}
