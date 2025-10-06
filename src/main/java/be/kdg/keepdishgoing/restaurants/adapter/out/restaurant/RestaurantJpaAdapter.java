package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;

import be.kdg.keepdishgoing.restaurants.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.UpdateRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantJpaAdapter implements UpdateRestaurantPort, LoadRestaurantPort {

    private static final Logger log = LoggerFactory.getLogger(RestaurantJpaAdapter.class);

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final Mapper mapper;

    public RestaurantJpaAdapter(RestaurantJpaRepository restaurantJpaRepository, Mapper mapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Restaurant> loadByOwner(OwnerId ownerId) {
        log.debug("Loading restaurant for owner: {}", ownerId.id());
        return restaurantJpaRepository.findByOwnerId(ownerId.id())
                .map(mapper::toDomainRestaurant);
    }

    @Override
    public Optional<Restaurant> loadByRestaurantId(RestaurantId restaurantId) {
        log.debug("Loading restaurant by ID: {}", restaurantId.id());
        return restaurantJpaRepository.findByUuid(restaurantId.id())
                .map(mapper::toDomainRestaurant);
    }

//    @Override
//    public Restaurant save(Restaurant restaurant) {
//        log.debug("Saving restaurant: {}", restaurant.getName());
//        var entity = mapper.toEntityRestaurant(restaurant);
//        var savedEntity = restaurantJpaRepository.save(entity);
//        return mapper.toDomainRestaurant(savedEntity);
//    }
//
//    @Override
//    public void update(Restaurant restaurant) {
//        log.debug("Updating restaurant: {}", restaurant.getRestaurantId().id());
//        var entity = mapper.toEntityRestaurant(restaurant);
//        restaurantJpaRepository.save(entity);
//    }

    @Override
    public void delete(RestaurantId restaurantId) {
        log.debug("Deleting restaurant: {}", restaurantId.id());
        restaurantJpaRepository.deleteByUuid(restaurantId.id());
    }
}