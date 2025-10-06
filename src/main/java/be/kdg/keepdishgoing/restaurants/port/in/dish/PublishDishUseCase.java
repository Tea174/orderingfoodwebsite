package be.kdg.keepdishgoing.restaurants.port.in.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;

public interface PublishDishUseCase {
    void publishDish(DishId dishId);
    void unpublishDish(DishId dishId);
    void markOutOfStock(DishId dishId);
}