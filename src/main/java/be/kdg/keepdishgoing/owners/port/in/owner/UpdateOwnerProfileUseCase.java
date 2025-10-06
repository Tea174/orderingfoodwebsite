package be.kdg.keepdishgoing.owners.port.in.owner;

import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;

public interface UpdateOwnerProfileUseCase {
    void updateProfile(UpdateProfileCommand command);

    record UpdateProfileCommand(
            OwnerId ownerId,
            String firstName,
            String lastName,
            int phoneNumber,
            String address
    ) {}
}