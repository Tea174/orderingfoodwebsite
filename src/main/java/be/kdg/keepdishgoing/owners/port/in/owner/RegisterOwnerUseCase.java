package be.kdg.keepdishgoing.owners.port.in.owner;

import be.kdg.keepdishgoing.owners.domain.OwnerId;

public interface RegisterOwnerUseCase {
    OwnerId register(RegisterOwnerCommand command);

    record RegisterOwnerCommand(
            String firstName,
            String lastName,
            String email,
            String password,
            int phoneNumber,
            String address
    ) {}
}