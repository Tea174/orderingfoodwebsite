package be.kdg.keepdishgoing.orders.adapter.out.basket;

import be.kdg.keepdishgoing.orders.adapter.out.basket.basketItem.BasketItemJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "baskets", schema = "kdg_orders")
public class BasketJpaEntity {
    @Id
    @Column(name = "basket_id")
    private UUID basketId;

    @Column(name = "customer_id", nullable = true)
    private UUID customerId;

    @Column(name = "restaurant_id", nullable = true)
    private UUID restaurantId;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItemJpaEntity> items = new ArrayList<>();
}