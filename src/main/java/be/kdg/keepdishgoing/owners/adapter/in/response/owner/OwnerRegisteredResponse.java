package be.kdg.keepdishgoing.owners.adapter.in.response.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;

import java.util.UUID;

public record OwnerRegisteredResponse(
        UUID ownerId,
        String email,
        String fullName,
        String message
) {
    public static OwnerRegisteredResponse fromDomain(Owner owner) {
        return new OwnerRegisteredResponse(
                owner.getOwnerId().id(),
                owner.getEmail(),
                owner.getFullName(),
                "Registration successful"
        );
    }
}
