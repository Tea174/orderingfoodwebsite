package be.kdg.keepdishgoing.owners.port.out.owner;

import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;

public interface DeleteOwnerPort {
    void delete(OwnerId ownerId);
}