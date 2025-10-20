package be.kdg.keepdishgoing.common.event.restaurantEvents;

import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

// Record auto-generates constructor and eventPit() method

public record RestaurantCreatedEvent(
        LocalDateTime eventPit,
        UUID restaurantId,
        String ownerKeycloakId,
        String restaurantName,
        String address,
        String email,
        String pictureURL,
        TypeOfCuisine cuisine,
        Integer preparationTime,
        Time openingTime,
        Double minPrice,
        Double maxPrice,
        Time estimatedDeliveryTime
) implements DomainEvent {


}
