package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;

public interface SaveDishPort {
    Dish save(Dish dish);

}