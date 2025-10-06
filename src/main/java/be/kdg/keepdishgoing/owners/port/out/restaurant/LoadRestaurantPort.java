package be.kdg.keepdishgoing.owners.port.out.restaurant;

import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;
import be.kdg.keepdishgoing.owners.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;

import java.util.Optional;

public interface LoadRestaurantPort {
    Optional<Restaurant> loadByOwner(OwnerId ownerId);
    Optional<Restaurant> loadByRestaurantId(RestaurantId restaurantId);
}
