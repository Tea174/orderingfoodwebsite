package be.kdg.keepdishgoing.restaurants.adapter.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.port.out.dish.UpdateDishPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DishEventPublisher implements UpdateDishPort {

    private final ApplicationEventPublisher eventPublisher;

    public DishEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    @Override
    public Dish update(Dish dish) {
        dish.getDishEvents().forEach(eventPublisher::publishEvent);
        return dish;
    }

}