package be.kdg.keepdishgoing.delivery.adapter.out.orderProjector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProjectorJpaRepository extends JpaRepository<OrderProjectorEntity, UUID> {

}
