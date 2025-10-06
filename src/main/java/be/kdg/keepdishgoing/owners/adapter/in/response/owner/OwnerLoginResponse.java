package be.kdg.keepdishgoing.owners.adapter.in.response.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;

import java.util.UUID;

public record OwnerLoginResponse(
        UUID ownerId,
        String email,
        String fullName,
        String message
) {
    public static OwnerLoginResponse fromDomain(Owner owner) {
        return new OwnerLoginResponse(
                owner.getOwnerId().id(),
                owner.getEmail(),
                owner.getFullName(),
                "Login successful"
        );
    }
}