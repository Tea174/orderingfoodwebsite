package be.kdg.keepdishgoing.restaurants.port.in.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

public interface GetRestaurantUseCase {
    Restaurant getRestaurantById(RestaurantId RestaurantId);
    Restaurant getRestaurantByOwnerId(OwnerId ownerId);
}
