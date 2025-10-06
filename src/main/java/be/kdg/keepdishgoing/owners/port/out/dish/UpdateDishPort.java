package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.dish.Dish;

public interface UpdateDishPort {
    Dish update(Dish dish); // Will also publish events
}