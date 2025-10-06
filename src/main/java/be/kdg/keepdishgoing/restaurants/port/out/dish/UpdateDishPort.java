package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;

public interface UpdateDishPort {
    Dish update(Dish dish); // Will also publish events
}