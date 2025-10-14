package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;


import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.PublishRestaurantEventsPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.SaveRestaurantPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventPublisher implements PublishRestaurantEventsPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RestaurantEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvents(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        restaurant.clearDomainEvents();
    }
}
