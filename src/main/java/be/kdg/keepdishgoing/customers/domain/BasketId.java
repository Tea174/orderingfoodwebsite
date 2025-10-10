package be.kdg.keepdishgoing.customers.domain;

import java.util.UUID;

public record BasketId(UUID id) {
    public static BasketId of(UUID id) {
        return new BasketId(id);
    }
    public static BasketId create() {
        return new BasketId(UUID.randomUUID());
    }
}
