package be.kdg.keepdishgoing.restaurants.port.out.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;

public interface PublishRestaurantEventsPort {
    void publishEvents(Restaurant restaurant);
}
