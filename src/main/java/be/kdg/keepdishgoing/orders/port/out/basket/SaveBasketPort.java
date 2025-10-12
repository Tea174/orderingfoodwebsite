package be.kdg.keepdishgoing.orders.port.out.basket;

import be.kdg.keepdishgoing.orders.domain.basket.Basket;

public interface SaveBasketPort {
    void save(Basket basket);
    void delete(Basket basket);
}
