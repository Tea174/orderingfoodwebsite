package be.kdg.keepdishgoing.orders.adapter.out.order;

import be.kdg.keepdishgoing.orders.adapter.out.order.orderItem.OrderItemJpaEntity;
import be.kdg.keepdishgoing.common.commonEnum.commonOrderEnum.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "kdg_orders")
public class OrderJpaEntity {
    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "customer_id")
    private UUID customerId;

    // Guest customer fields
    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "delivery_address", length = 500)
    private String deliveryAddress;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;


    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}