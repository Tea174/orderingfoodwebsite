package be.kdg.keepdishgoing.restaurants.core.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.CreateRestaurantUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.FilterRestaurantByTypeOfCuisine;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.GetRestaurantUseCase;
import be.kdg.keepdishgoing.restaurants.port.out.dish.PublishDishEventsPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.SaveDishPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.PublishRestaurantEventsPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.SaveRestaurantPort;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RestaurantService implements
        CreateRestaurantUseCase,
        GetRestaurantUseCase,
        FilterRestaurantByTypeOfCuisine {

    private final LoadRestaurantPort loadRestaurantPort;
    private final SaveRestaurantPort saveRestaurantPort;
    private final SaveDishPort saveDishPort;
    private final PublishRestaurantEventsPort publishRestaurantEventsPort;
    private final PublishDishEventsPort publishDishEventsPort;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Override
    public Restaurant getRestaurantByOwnerId(OwnerId ownerId) {
        logger.debug("Getting restaurant by owner id {}", ownerId);
        return loadRestaurantPort.loadByOwnerId(ownerId)
                .orElseThrow(() -> new IllegalStateException(
                        "No restaurant found for this owner. Please create a restaurant first."));
    }

    @Override
    public Optional<Restaurant> findByOwnerIdKeycloakSubjectId(String keycloakSubjectId) {
        logger.debug("Getting restaurant by owner keycloakId {}", keycloakSubjectId);
        return loadRestaurantPort.loadByOwnerKeycloakId(keycloakSubjectId);
    }

    @Override
    public RestaurantId createRestaurant(CreateRestaurantCommand command) {
        logger.debug("Check if restaurant exists");
        loadRestaurantPort.loadByEmail(command.email()).ifPresent(r -> {
            throw new IllegalArgumentException("email already registered");
        });

        // Create restaurant WITHOUT dishes first
        Restaurant restaurant = Restaurant.createRestaurant(
                command.ownerId(),
                command.ownerKeycloakId(),
                command.name(),
                command.address(),
                command.email(),
                command.pictureURL(),
                command.cuisine(),
                command.preparationTime(),
                command.openingTime(),
                command.minPrice(),
                command.maxPrice(),
                command.estimatedDeliveryTime(),
                List.of() // Empty list initially
        );

        Restaurant savedRestaurant = saveRestaurantPort.save(restaurant);
        publishRestaurantEventsPort.publishEvents(savedRestaurant);
        RestaurantId restaurantId = savedRestaurant.getRestaurantId();

        // create dishes linked to this restaurant
        if (command.dishCommands() != null && !command.dishCommands().isEmpty()) {
            command.dishCommands().forEach(dishCmd -> {
                Dish dish = Dish.createDish(
                        restaurantId, // Link to newly created restaurant
                        dishCmd.name(),
                        dishCmd.dishType(),
                        dishCmd.foodTags(),
                        dishCmd.description(),
                        dishCmd.price(),
                        dishCmd.pictureURL()
                );
                Dish savedDish = saveDishPort.save(dish);
                publishDishEventsPort.publishEvents(savedDish);
            });
        }

        return restaurantId;
    }

    @Override
    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(RestaurantId restaurantId) {
        logger.debug("Getting restaurant by id {}", restaurantId);
        Restaurant restaurant = loadRestaurantPort.loadByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("restaurant not found"));
        // Force initialization of lazy collections
        restaurant.getDishes().forEach(dish -> dish.getFoodTags().size());
        return restaurant;
    }


    @Override
    public List<Restaurant> filterRestaurantByTypeOfCuisine(TypeOfCuisine typeOfCuisine) {
        logger.debug("filter restaurant by type of cuisine {}", typeOfCuisine);
        List<Restaurant> restaurants = loadRestaurantPort.loadByType(typeOfCuisine);
        if (restaurants.isEmpty()) {
            throw new IllegalArgumentException("No restaurant found");
        }
        return restaurants;
    }
}
