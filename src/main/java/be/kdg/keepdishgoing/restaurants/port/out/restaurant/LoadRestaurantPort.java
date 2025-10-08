package be.kdg.keepdishgoing.restaurants.port.out.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

import java.util.Optional;

public interface LoadRestaurantPort {
    Optional<Restaurant> loadByOwner(OwnerId ownerId);
    Optional<Restaurant> loadByRestaurantId(RestaurantId restaurantId);
    Optional<Restaurant> loadByName(String name);
    Optional<Restaurant> loadByEmail(String email);
    Optional<Restaurant> findByOwnerId(OwnerId ownerId);

}
