package be.kdg.keepdishgoing.restaurants.adapter.in.request.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;

import java.util.UUID;

public record OwnerResponse(
        UUID ownerId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address
) {
    public static OwnerResponse fromDomain(Owner owner) {
        return new OwnerResponse(
                owner.getOwnerId().id(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail(),
                owner.getPhoneNumber(),
                owner.getAddress()
        );
    }
}