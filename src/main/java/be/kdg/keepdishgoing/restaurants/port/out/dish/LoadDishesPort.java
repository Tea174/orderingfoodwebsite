package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.dish.*;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

import java.util.List;
import java.util.Optional;

public interface LoadDishesPort {
    List<Dish> loadByRestaurantId(RestaurantId restaurantId);
    List<Dish> loadPublishedByRestaurantId(RestaurantId restaurantId);
    Optional<Dish> loadByPublished(DishId dishId);
    Optional<Dish> loadByDishId(DishId dishId);

    List<Dish> loadByType(DishType dishType);
    List<Dish> loadByFoodTag (FoodTag foodTag);

}
