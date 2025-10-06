package be.kdg.keepdishgoing.owners.port.in.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;
import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;

public interface GetOwnerUseCase {
    Owner getOwnerById(OwnerId ownerId);
    Owner getOwnerByEmail(String email);
}
