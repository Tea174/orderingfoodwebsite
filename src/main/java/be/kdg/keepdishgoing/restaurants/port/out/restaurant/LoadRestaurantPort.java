package be.kdg.keepdishgoing.restaurants.port.out.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantPort {
    Optional<Restaurant> loadByRestaurantId(RestaurantId restaurantId);
    Optional<Restaurant> loadByName(String name);
    Optional<Restaurant> loadByEmail(String email);
    Optional<Restaurant> loadByOwnerId(OwnerId ownerId);
    List<Restaurant> loadByType(TypeOfCuisine typeOfCuisine);
    String loadByNameById(UUID restaurantId);
    Optional<Restaurant> loadByOwnerKeycloakId(String keycloakId);


}
