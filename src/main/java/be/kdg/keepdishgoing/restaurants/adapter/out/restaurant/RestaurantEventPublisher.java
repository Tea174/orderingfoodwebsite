package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;


import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.SaveRestaurantPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventPublisher implements SaveRestaurantPort {
//    private final RestaurantJpaRepository restaurantJpaRepository;
//    private final Mapper mapper;
       private final ApplicationEventPublisher applicationEventPublisher;

//    public RestaurantEventPublisher(RestaurantJpaRepository restaurantJpaRepository, Mapper mapper, ApplicationEventPublisher applicationEventPublisher) {
//        this.restaurantJpaRepository = restaurantJpaRepository;
//        this.mapper = mapper;
//        this.applicationEventPublisher = applicationEventPublisher;
//    }


    public RestaurantEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }
}
