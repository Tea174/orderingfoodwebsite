package be.kdg.keepdishgoing.delivery.domain.delivery;

import be.kdg.keepdishgoing.common.event.DomainEvent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    private DeliveryId deliveryId;
    private UUID orderId;
    private UUID customerId;
    private UUID restaurantId;

    // Guest delivery details
    private String guestName;
    private String guestEmail;
    private String deliveryAddress;

    private DeliveryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime estimatedDeliveryTime;

    private String cancellationReason;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // Constructor for registered customer
    public static Delivery forCustomer(UUID orderId, UUID customerId, UUID restaurantId, String deliveryAddress) {
        Delivery delivery = new Delivery();
        delivery.deliveryId = DeliveryId.create();
        delivery.orderId = orderId;
        delivery.customerId = customerId;
        delivery.restaurantId = restaurantId;
        delivery.deliveryAddress = deliveryAddress;
        delivery.status = DeliveryStatus.PENDING;
        delivery.pickedUpAt = LocalDateTime.now();
        delivery.deliveredAt = LocalDateTime.now();
        delivery.estimatedDeliveryTime = LocalDateTime.now();
        return delivery;

    }

    // Constructor for guest
    public static Delivery forGuest(UUID orderId, UUID restaurantId, String guestName,
                    String guestEmail, String deliveryAddress) {
        Delivery delivery = new Delivery();
        delivery.deliveryId = DeliveryId.create();
        delivery.orderId = orderId;
        delivery.customerId = null;
        delivery.restaurantId = restaurantId;
        delivery.deliveryAddress = deliveryAddress;
        delivery.status = DeliveryStatus.PENDING;
        delivery.pickedUpAt = LocalDateTime.now();
        delivery.deliveredAt = LocalDateTime.now();
        delivery.estimatedDeliveryTime = LocalDateTime.now();
        return delivery;
    }

    public boolean isGuestDelivery() {
        return customerId == null;
    }

    public void calculateEstimatedDeliveryTime(int deliveryTimeMinutes,
                                               int preparationTimeMinutes,
                                               int busynessFactor) {
        int totalMinutes = (deliveryTimeMinutes + preparationTimeMinutes) * busynessFactor;
        this.estimatedDeliveryTime = LocalDateTime.now().plusMinutes(totalMinutes);
    }

    public void markAsAssigned() {
        if (this.status != DeliveryStatus.PENDING) {
            throw new IllegalStateException("Can only assign delivery when status is PENDING");
        }
        this.status = DeliveryStatus.ASSIGNED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPickedUp() {
        if (this.status != DeliveryStatus.ASSIGNED && this.status != DeliveryStatus.PENDING) {
            throw new IllegalStateException("Can only pick up delivery when status is ASSIGNED or PENDING");
        }
        this.status = DeliveryStatus.PICKED_UP;
        this.pickedUpAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDelivered() {
        if (this.status != DeliveryStatus.PICKED_UP) {
            throw new IllegalStateException("Can only deliver when status is PICKED_UP");
        }
        this.status = DeliveryStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsCancelled(String reason) {  // Added reason parameter
        if (this.status == DeliveryStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel a delivered order");
        }
        this.status = DeliveryStatus.CANCELLED;
        this.cancellationReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(DeliveryStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isInProgress() {
        return this.status == DeliveryStatus.ASSIGNED ||
                this.status == DeliveryStatus.PICKED_UP;
    }

    public boolean isCompleted() {
        return this.status == DeliveryStatus.DELIVERED;
    }

    public boolean isPending() {
        return this.status == DeliveryStatus.PENDING;
    }

    public String getStatusMessage() {
        return switch (status) {
            case PENDING -> "Preparing your order";
            case ASSIGNED -> "Driver assigned";
            case PICKED_UP -> "Driver picked up - on the way!";
            case IN_TRANSIT -> "Delivering....";
            case DELIVERED -> "Delivered!";
            case CANCELLED -> "Cancelled";
        };
    }
}