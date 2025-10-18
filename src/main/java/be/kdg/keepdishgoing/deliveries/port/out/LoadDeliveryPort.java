package be.kdg.keepdishgoing.deliveries.port.out;

import be.kdg.keepdishgoing.deliveries.domain.delivery.Delivery;
import be.kdg.keepdishgoing.deliveries.domain.delivery.DeliveryStatus;

import java.util.Optional;
import java.util.UUID;

public interface LoadDeliveryPort {
    Delivery save(Delivery delivery);
    Optional<Delivery> findByOrderId(UUID orderId);
    long countByRestaurantIdAndStatus(UUID restaurantId, DeliveryStatus status);
}
