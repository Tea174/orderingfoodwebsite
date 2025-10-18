package be.kdg.keepdishgoing.deliveries.adapter.out.delivery;

import be.kdg.keepdishgoing.deliveries.domain.delivery.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deliveries", schema = "kdg_deliveries")
@Getter
@Setter
public class DeliveryJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = true)
    private UUID customerId;

    @Column(nullable = false)
    private UUID restaurantId;

    // Guest delivery details (only populated for guest orders)
    @Column(length = 255)
    private String guestName;

    @Column(length = 255)
    private String guestEmail;

    @Column(length = 500, nullable = false)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DeliveryStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime pickedUpAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime estimatedDeliveryTime;

    @Column(length = 500)
    private String cancellationReason;
}