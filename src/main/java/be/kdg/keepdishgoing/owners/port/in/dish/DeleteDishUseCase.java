package be.kdg.keepdishgoing.owners.port.in.dish;

import be.kdg.keepdishgoing.owners.domain.dish.DishId;

public interface DeleteDishUseCase {
    void deleteDish(DishId dishId);
}