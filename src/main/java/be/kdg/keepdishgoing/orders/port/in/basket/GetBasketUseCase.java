package be.kdg.keepdishgoing.orders.port.in.basket;

import be.kdg.keepdishgoing.orders.domain.basket.Basket;
import be.kdg.keepdishgoing.orders.domain.basket.BasketId;

public interface GetBasketUseCase {
    Basket getBasketById(BasketId basketId);
}
