package be.kdg.keepdishgoing.owners.port.out.restaurant;

import be.kdg.keepdishgoing.owners.domain.OwnerId;
import be.kdg.keepdishgoing.owners.domain.Restaurant;
import be.kdg.keepdishgoing.owners.domain.RestaurantId;

import java.util.Optional;

public interface LoadRestaurantPort {
    Optional<Restaurant> loadByOwner(OwnerId ownerId);
    Optional<Restaurant> loadByRestaurantId(RestaurantId restaurantId);
}
