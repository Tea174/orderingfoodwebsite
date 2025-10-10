package be.kdg.keepdishgoing.customers.domain;

import java.util.UUID;

public record CustomerId(UUID id) {
    public static CustomerId create() {
        return new CustomerId(UUID.randomUUID());
    }
    public static CustomerId of(UUID id) {
        return new CustomerId(id);
    }
}
