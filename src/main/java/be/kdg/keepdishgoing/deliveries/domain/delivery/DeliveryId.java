package be.kdg.keepdishgoing.deliveries.domain.delivery;

import java.util.UUID;

public record DeliveryId(UUID id) {
    public static DeliveryId of(UUID id) {
        return new DeliveryId(id);
    }
    public static DeliveryId create() {
        return new DeliveryId(UUID.randomUUID());
    }
}
