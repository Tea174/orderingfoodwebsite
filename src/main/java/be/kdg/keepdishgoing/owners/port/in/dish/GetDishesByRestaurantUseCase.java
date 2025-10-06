package be.kdg.keepdishgoing.owners.port.in.dish;

import be.kdg.keepdishgoing.owners.domain.dish.Dish;
import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;

import java.util.List;

public interface GetDishesByRestaurantUseCase {
    List<Dish> getDishesByRestaurant(RestaurantId restaurantId);
}