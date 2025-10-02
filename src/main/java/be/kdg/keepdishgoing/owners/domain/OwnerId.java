package be.kdg.keepdishgoing.owners.domain;

import java.util.UUID;

public record OwnerId(UUID id) {
    public static OwnerId of(UUID id) {
        return new OwnerId(id);
    }

    public static OwnerId create() {
        return new OwnerId(UUID.randomUUID());
    }
}
