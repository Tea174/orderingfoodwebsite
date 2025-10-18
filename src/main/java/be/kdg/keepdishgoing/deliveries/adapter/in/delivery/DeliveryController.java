package be.kdg.keepdishgoing.deliveries.adapter.in.delivery;

import be.kdg.keepdishgoing.deliveries.domain.delivery.Delivery;
import be.kdg.keepdishgoing.deliveries.port.in.DeliveryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/deliveries")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryUseCase deliveryUseCase;

    @GetMapping("/{orderId}")
    public ResponseEntity<DeliveryResponse> getDeliveryStatus(@PathVariable UUID orderId) {
        Delivery delivery = deliveryUseCase.getDeliveryByOrderId(orderId);

        DeliveryResponse response = new DeliveryResponse(
                delivery.getOrderId(),
                delivery.getCustomerId(),
                delivery.getStatus().name(),
                delivery.getStatusMessage(),
                delivery.getEstimatedDeliveryTime(),
                delivery.getDeliveryAddress(),
                delivery.isGuestDelivery()
        );

        return ResponseEntity.ok(response);
    }

    record DeliveryResponse(
            UUID orderId,
            UUID customerId,  // Can be null for guest
            String status,
            String statusMessage,
            java.time.LocalDateTime estimatedDeliveryTime,
            String deliveryAddress,
            boolean isGuestDelivery
    ) {}
}