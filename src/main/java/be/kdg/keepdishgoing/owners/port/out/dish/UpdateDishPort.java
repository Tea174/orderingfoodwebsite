package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.Dish;

public interface UpdateDishPort {
    Dish update(Dish dish); // Will also publish events
}