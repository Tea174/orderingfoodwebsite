package be.kdg.keepdishgoing.orders.adapter.out.order.orderItem;

import be.kdg.keepdishgoing.orders.adapter.out.order.OrderJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "order_items", schema = "kdg_orders")
public class OrderItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    @Column(name = "dish_id", nullable = false)
    private UUID dishId;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private int quantity;
}
