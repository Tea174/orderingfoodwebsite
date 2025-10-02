package be.kdg.keepdishgoing.owners.port.in.dish;

import be.kdg.keepdishgoing.owners.domain.DishId;

public interface PublishDishUseCase {
    void publishDish(DishId dishId);
    void unpublishDish(DishId dishId);
    void markOutOfStock(DishId dishId);
}