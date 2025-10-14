package be.kdg.keepdishgoing.restaurants.port.in.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.restaurants.port.in.dish.AddDishUseCase;

import java.sql.Time;
import java.util.List;

public interface CreateRestaurantUseCase {
    RestaurantId createRestaurant(CreateRestaurantCommand createRestaurantCommand);
    record CreateRestaurantCommand(
            OwnerId ownerId,
            String ownerKeycloakId,
            String name,
            String address,
            String email,
            String pictureURL,
            TypeOfCuisine cuisine,
            Time preparationTime,
            Time openingTime,
            Double minPrice,
            Double maxPrice,
            Time estimatedDeliveryTime,
            List<AddDishUseCase.AddDishCommand> dishCommands
    )
    {
        public CreateRestaurantCommand {}
    }
}
