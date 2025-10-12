package be.kdg.keepdishgoing.orders.adapter.out.basket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasketJpaRepository extends JpaRepository<BasketJpaEntity, UUID> {
    Optional<BasketJpaEntity> findByCustomerId(UUID customerId);
    void deleteByCustomerId(UUID customerId);
}
