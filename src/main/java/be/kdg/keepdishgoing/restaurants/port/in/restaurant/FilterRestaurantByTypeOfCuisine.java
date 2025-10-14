package be.kdg.keepdishgoing.restaurants.port.in.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;

import java.util.List;

public interface FilterRestaurantByTypeOfCuisine {
    List<Restaurant> filterRestaurantByTypeOfCuisine(TypeOfCuisine typeOfCuisine);
}
