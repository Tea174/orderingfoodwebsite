package be.kdg.keepdishgoing.orders.adapter.out.order.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemJpaEntity, UUID> {
}
