package be.kdg.keepdishgoing.orders.port.out.basket;

import be.kdg.keepdishgoing.orders.domain.basket.Basket;
import java.util.Optional;
import java.util.UUID;

public interface LoadBasketPort {
    Optional<Basket> loadByCustomerId(UUID customerId);
    Optional<Basket> loadById(UUID basketId);
}
