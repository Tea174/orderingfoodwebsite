package be.kdg.keepdishgoing.orders.adapter.out.customerProjector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerProjectorJpaRepository extends JpaRepository<CustomerProjectorEntity, UUID> {
    Optional<CustomerProjectorEntity> findByKeycloakId(String keycloakId);
}