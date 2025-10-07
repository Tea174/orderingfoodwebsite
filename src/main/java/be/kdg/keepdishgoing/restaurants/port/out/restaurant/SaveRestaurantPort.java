package be.kdg.keepdishgoing.restaurants.port.out.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

public interface UpdateRestaurantPort {
    Restaurant update(Restaurant restaurant);
}
