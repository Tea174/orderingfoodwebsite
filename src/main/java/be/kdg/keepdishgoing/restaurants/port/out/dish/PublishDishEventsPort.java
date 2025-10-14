package be.kdg.keepdishgoing.restaurants.port.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;

public interface PublishDishEventsPort {
    void publishEvents(Dish dish);
}
