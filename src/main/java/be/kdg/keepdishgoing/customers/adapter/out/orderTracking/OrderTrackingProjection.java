package be.kdg.keepdishgoing.customers.adapter.out.orderTracking;

import be.kdg.keepdishgoing.common.commonEnum.commonOrderEnum.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_tracking", schema = "kdg_customers")
public class OrderTrackingProjection {
    @Id
    private UUID orderId;

    private UUID customerId; // null for guests
    private String trackingToken; // for guest access

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PLACED, ACCEPTED, READY, ASSIGNED, ON_THE_WAY, DELIVERED

    private String restaurantName;
    private String deliveryDriverName;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime lastUpdated;
}