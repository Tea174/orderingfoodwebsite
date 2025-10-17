package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;

import be.kdg.keepdishgoing.restaurants.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.SaveRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class RestaurantJpaAdapter implements SaveRestaurantPort, LoadRestaurantPort{

    private static final Logger log = LoggerFactory.getLogger(RestaurantJpaAdapter.class);

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final Mapper mapper;

    public RestaurantJpaAdapter(RestaurantJpaRepository restaurantJpaRepository, Mapper mapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Restaurant> loadByRestaurantId(RestaurantId restaurantId) {
        log.debug("Loading restaurant by ID: {}", restaurantId.id());
        return restaurantJpaRepository.findById(restaurantId.id())
                .map(mapper::toDomainRestaurant);
    }

    @Override
    public Optional<Restaurant> loadByName(String name) {
        return restaurantJpaRepository.findByName(name)
                .map(mapper::toDomainRestaurant);
    }

    @Override
    public Optional<Restaurant> loadByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> loadByOwnerId(OwnerId ownerId) {
        log.debug("Loading restaurant for owner: {}", ownerId.id());
        return restaurantJpaRepository.findByOwnerId_Uuid(ownerId.id())
                .map(mapper::toDomainRestaurant);
    }

    @Override
    public List<Restaurant> loadByType(TypeOfCuisine typeOfCuisine) {
        log.debug("Loading restaurant for type: {}", typeOfCuisine.name());
        return restaurantJpaRepository.findByCuisine(typeOfCuisine)
                .stream()
                .map(mapper::toDomainRestaurant)
                .toList();
    }

    @Override
    public String loadByNameById(UUID restaurantId) {
        return restaurantJpaRepository.nameById(restaurantId);
    }

    @Override
    public Optional<Restaurant> loadByOwnerKeycloakId(String keycloakId) {
        log.debug("Loading restaurant for owner (in restaurantJpaAdapter): {}", keycloakId);
        return restaurantJpaRepository.findByOwnerKeycloakId(keycloakId)
                .map(mapper::toDomainRestaurant);
    }


    @Override
    public Restaurant save(Restaurant restaurant) {
        log.debug("Saving restaurant: {}", restaurant.getName());
        var entity = mapper.toEntityRestaurant(restaurant);
        var savedEntity = restaurantJpaRepository.save(entity);
        System.out.println(savedEntity);
        return restaurant;
    }


//
//    @Override
//    public void update(RestaurantProjectorRecord restaurant) {
//        log.debug("Updating restaurant: {}", restaurant.getRestaurantId().id());
//        var entity = mapper.toEntityRestaurant(restaurant);
//        restaurantJpaRepository.save(entity);
//    }


}