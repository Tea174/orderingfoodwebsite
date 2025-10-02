package be.kdg.keepdishgoing.owners.port.in.owner;

import be.kdg.keepdishgoing.owners.domain.Owner;
import be.kdg.keepdishgoing.owners.domain.OwnerId;

public interface GetOwnerUseCase {
    Owner getOwnerById(OwnerId ownerId);
    Owner getOwnerByEmail(String email);
}
