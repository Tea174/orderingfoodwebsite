package be.kdg.keepdishgoing.owners.port.out.restaurant;

import be.kdg.keepdishgoing.owners.domain.RestaurantId;

public interface UpdateRestaurantPort {
    void delete(RestaurantId restaurantId);
}
