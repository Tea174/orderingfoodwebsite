package be.kdg.keepdishgoing.orders.domain.order;

import java.util.UUID;

public record OrderId(UUID id) {
    public static OrderId create() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId of(UUID id) {
        return new OrderId(id);
    }
}
