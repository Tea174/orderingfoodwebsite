package be.kdg.keepdishgoing.owners.port.in;

import be.kdg.keepdishgoing.owners.domain.Dish;

public interface AddDishesPort {
    Dish addDish(Dish dish);
}
