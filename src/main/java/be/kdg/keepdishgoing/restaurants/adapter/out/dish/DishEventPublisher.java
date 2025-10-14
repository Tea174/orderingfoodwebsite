package be.kdg.keepdishgoing.restaurants.adapter.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.port.out.dish.PublishDishEventsPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DishEventPublisher implements PublishDishEventsPort {

    private final ApplicationEventPublisher eventPublisher;

    public DishEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishEvents(Dish dish) {
        dish.getDomainEvents().forEach(eventPublisher::publishEvent);
        dish.clearDomainEvents();
    }
}