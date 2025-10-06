package be.kdg.keepdishgoing.restaurants.port.out.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;

import java.util.Optional;

public interface LoadOwnerPort {
    Optional<Owner> loadById(OwnerId ownerId);
    Optional<Owner> loadByEmail(String email);
}