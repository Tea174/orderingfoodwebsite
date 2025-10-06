package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;

public interface DeleteDishPort {
    void delete(DishId dishId);
}