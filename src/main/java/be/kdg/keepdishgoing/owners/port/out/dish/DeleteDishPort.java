package be.kdg.keepdishgoing.owners.port.out.dish;

import be.kdg.keepdishgoing.owners.domain.dish.DishId;

public interface DeleteDishPort {
    void delete(DishId dishId);
}