package be.kdg.keepdishgoing.deliveries.adapter.out.delivery;

import be.kdg.keepdishgoing.deliveries.domain.delivery.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryJpaEntity, UUID> {
    Optional<DeliveryJpaEntity> findByOrderId(UUID orderId);
    long countByRestaurantIdAndStatus(UUID restaurantId, DeliveryStatus status);
}
