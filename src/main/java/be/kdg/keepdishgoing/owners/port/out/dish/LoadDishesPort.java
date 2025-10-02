package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.Dish;
import be.kdg.keepdishgoing.owners.domain.DishId;
import be.kdg.keepdishgoing.owners.domain.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface LoadDishesPort {
    Optional<Dish> loadByDishId(DishId dishId);
    List<Dish> loadByRestaurantId(RestaurantId restaurantId);
}
