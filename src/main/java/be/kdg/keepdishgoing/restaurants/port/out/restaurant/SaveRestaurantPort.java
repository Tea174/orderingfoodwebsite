package be.kdg.keepdishgoing.restaurants.port.out.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;

public interface SaveRestaurantPort {
    Restaurant save(Restaurant restaurant);
}
