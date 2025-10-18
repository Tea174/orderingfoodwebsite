package be.kdg.keepdishgoing.restaurants.port.in.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;

public interface UpdateOwnerProfileUseCase {
    void updateProfile(UpdateProfileCommand command);

    record UpdateProfileCommand(
            OwnerId ownerId,
            String firstName,
            String lastName,
            String phoneNumber,
            String address
    ) {}
}