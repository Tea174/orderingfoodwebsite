package be.kdg.keepdishgoing.owners.port.out.restaurant;

import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;

public interface UpdateRestaurantPort {
    void delete(RestaurantId restaurantId);
}
