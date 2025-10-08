package be.kdg.keepdishgoing.restaurants.core.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.CreateRestaurantUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.GetRestaurantUseCase;
import be.kdg.keepdishgoing.restaurants.port.out.dish.SaveDishPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.SaveRestaurantPort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RestaurantService implements
        CreateRestaurantUseCase,
        GetRestaurantUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final SaveRestaurantPort saveRestaurantPort;
    private final SaveDishPort saveDishPort;


    @Override
    public Restaurant getRestaurantByOwnerId(OwnerId ownerId) {
        return loadRestaurantPort.findByOwnerId(ownerId)
                .orElseThrow(() -> new IllegalStateException(
                        "No restaurant found for this owner. Please create a restaurant first."));
    }



    @Override
    public RestaurantId createRestaurant(CreateRestaurantCommand command) {
        // Check if restaurant exists
        loadRestaurantPort.loadByEmail(command.email()).ifPresent(r -> {
            throw new IllegalArgumentException("email already registered");
        });

        // Create restaurant WITHOUT dishes first
        Restaurant restaurant = Restaurant.createRestaurant(
                command.ownerId(),
                command.name(),
                command.address(),
                command.email(),
                command.pictureURL(),
                command.cuisine(),
                command.preparationTime(),
                command.openingTime(),
                List.of() // Empty list initially
        );

        Restaurant savedRestaurant = saveRestaurantPort.save(restaurant);
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
                saveDishPort.save(dish);
            });
        }

        return restaurantId;
    }

    @Override
    public Restaurant getRestaurantById(RestaurantId restaurantId) {
        return loadRestaurantPort.loadByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("restaurant not found"));
    }


}
