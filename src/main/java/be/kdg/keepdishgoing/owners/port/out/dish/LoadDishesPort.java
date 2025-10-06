package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.dish.Dish;
import be.kdg.keepdishgoing.owners.domain.dish.DishId;
import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface LoadDishesPort {
    Optional<Dish> loadByDishId(DishId dishId);
    List<Dish> loadByRestaurantId(RestaurantId restaurantId);
}
