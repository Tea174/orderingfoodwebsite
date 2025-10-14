package be.kdg.keepdishgoing.orders.domain.restaurantRecord;

import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;

import java.sql.Time;
import java.util.UUID;

public record RestaurantProjectorRecord(
        UUID restaurantId,
        String ownerKeycloakId,
        String name,
        String address,
        TypeOfCuisine cuisine,
        Time estimatedDeliveryTime
) {}