package be.kdg.keepdishgoing.restaurants.adapter.out.purchaseProjector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseProjectorJpaRepository extends JpaRepository<PurchaseProjectorEntity, UUID> {

}
