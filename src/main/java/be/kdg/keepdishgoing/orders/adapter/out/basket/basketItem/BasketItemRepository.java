package be.kdg.keepdishgoing.orders.adapter.out.basket.basketItem;

import be.kdg.keepdishgoing.orders.adapter.out.basket.BasketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketItemRepository extends JpaRepository<BasketJpaEntity, UUID> {
}
