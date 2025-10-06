package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.dish.Dish;

public interface SaveDishPort {
    Dish save(Dish dish);
}