package be.kdg.keepdishgoing.orders.port.out.restaurantProjector;

import be.kdg.keepdishgoing.orders.domain.restaurantRecord.RestaurantProjectorRecord;

import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantProjectorPort {
     Optional<RestaurantProjectorRecord> findById (UUID restaurantId);
}
