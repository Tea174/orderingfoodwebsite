package be.kdg.keepdishgoing.owners.port.in;

import be.kdg.keepdishgoing.owners.domain.Dish;

public interface AdjustDishesPort {
    Dish adjustDish(Dish dish);
}
