package be.kdg.keepdishgoing.orders.adapter.out.order;


import be.kdg.keepdishgoing.orders.domain.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    Page<OrderJpaEntity> findByCustomerIdOrderByCreatedAtDesc(
            UUID customerId,
            Pageable pageable
    );

    List<OrderJpaEntity> findByRestaurantId(UUID restaurantId);

    List<OrderJpaEntity> findByRestaurantIdAndStatus(
            UUID restaurantId,
            OrderStatus status
    );
}
