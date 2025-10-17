package be.kdg.keepdishgoing.orders.adapter.out.restaurantProjector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantProjectorJpaRepository extends JpaRepository<RestaurantProjectorEntity, UUID> {
    Optional<RestaurantProjectorEntity> findByOwnerKeycloakId(String keycloakId);
}