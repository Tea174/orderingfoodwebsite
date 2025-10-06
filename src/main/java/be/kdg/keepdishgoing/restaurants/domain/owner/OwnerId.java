package be.kdg.keepdishgoing.restaurants.domain.owner;

import java.util.UUID;

public record OwnerId(UUID id) {
    public static OwnerId of(UUID id) {
        return new OwnerId(id);
    }

    public static OwnerId create() {
        return new OwnerId(UUID.randomUUID());
    }
}
