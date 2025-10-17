package be.kdg.keepdishgoing.delivery.adapter.out.delivery;

import be.kdg.keepdishgoing.delivery.domain.delivery.Delivery;
import be.kdg.keepdishgoing.delivery.domain.delivery.DeliveryId;
import be.kdg.keepdishgoing.delivery.domain.delivery.DeliveryStatus;
import be.kdg.keepdishgoing.delivery.port.out.LoadDeliveryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeliveryJpaAdapter implements LoadDeliveryPort {

    private final DeliveryJpaRepository deliveryJpaRepository;
    public DeliveryJpaAdapter(DeliveryJpaRepository deliveryJpaRepository) {
        this.deliveryJpaRepository = deliveryJpaRepository;
    }

    @Override
    public Delivery save(Delivery delivery) {
        DeliveryJpaEntity entity = toEntity(delivery);
        DeliveryJpaEntity saved = deliveryJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Delivery> findByOrderId(UUID orderId) {
        return deliveryJpaRepository.findByOrderId(orderId)
                .map(this::toDomain);
    }

    @Override
    public long countByRestaurantIdAndStatus(UUID restaurantId, DeliveryStatus status) {
        return deliveryJpaRepository.countByRestaurantIdAndStatus(restaurantId, status);
    }


    private DeliveryJpaEntity toEntity(Delivery delivery) {
        DeliveryJpaEntity entity = new DeliveryJpaEntity();
        entity.setId(delivery.getDeliveryId().id());
        entity.setOrderId(delivery.getOrderId());
        entity.setRestaurantId(delivery.getRestaurantId());
        entity.setStatus(delivery.getStatus());
        entity.setCreatedAt(delivery.getCreatedAt());
        entity.setPickedUpAt(delivery.getPickedUpAt());
        entity.setDeliveredAt(delivery.getDeliveredAt());
        entity.setEstimatedDeliveryTime(delivery.getEstimatedDeliveryTime());
        return entity;
    }

    private Delivery toDomain(DeliveryJpaEntity entity) {
        Delivery delivery;

        // Use appropriate factory method based on whether it's a guest delivery
        if (entity.getCustomerId() == null) {
            delivery = Delivery.forGuest(
                    entity.getOrderId(),
                    entity.getRestaurantId(),
                    entity.getGuestName(),
                    entity.getGuestEmail(),
                    entity.getDeliveryAddress()
            );
        } else {
            delivery = Delivery.forCustomer(
                    entity.getOrderId(),
                    entity.getCustomerId(),
                    entity.getRestaurantId(),
                    entity.getDeliveryAddress()
            );
        }

        // Override the auto-generated values with persisted ones
        delivery.setDeliveryId(new DeliveryId(entity.getId()));
        delivery.setStatus(entity.getStatus());
        delivery.setCreatedAt(entity.getCreatedAt());
        delivery.setUpdatedAt(entity.getUpdatedAt());
        delivery.setPickedUpAt(entity.getPickedUpAt());
        delivery.setDeliveredAt(entity.getDeliveredAt());
        delivery.setEstimatedDeliveryTime(entity.getEstimatedDeliveryTime());
        delivery.setCancellationReason(entity.getCancellationReason());

        return delivery;
    }
}