package be.kdg.keepdishgoing.owners.port.in.dish;

import be.kdg.keepdishgoing.owners.domain.Dish;
import be.kdg.keepdishgoing.owners.domain.RestaurantId;

import java.util.List;

public interface GetDishesByRestaurantUseCase {
    List<Dish> getDishesByRestaurant(RestaurantId restaurantId);
}