package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.TypeOfCuisine;

import java.util.List;
import java.util.Optional;

public interface LoadDishesPort {
    Optional<Dish> loadByDishId(DishId dishId);
    List<Dish> loadByRestaurantId(RestaurantId restaurantId);
    List<Dish> loadByType(DishType dishType);
    List<Dish> loadByFoodTag (FoodTag foodTag);
}
