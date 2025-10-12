package be.kdg.keepdishgoing.restaurants.port.in.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;

import java.util.List;

public interface GetDishUseCase {
    Dish getDishById(DishId dishId);
    List<PublishedDishDto> getPublishedDishesByRestaurant(RestaurantId restaurantId);
    PublishedDishDto getPublishedDish(DishId dishId);
    record PublishedDishDto(
            DishId dishId,
            String name,
            Double price,
            boolean inStock,
            RestaurantId restaurantId
    ) {}
}