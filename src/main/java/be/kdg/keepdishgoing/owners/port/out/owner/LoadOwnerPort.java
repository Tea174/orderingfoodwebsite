package be.kdg.keepdishgoing.owners.port.out.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;
import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;

import java.util.Optional;

public interface LoadOwnerPort {
    Optional<Owner> loadById(OwnerId ownerId);
    Optional<Owner> loadByEmail(String email);
}