package be.kdg.keepdishgoing.owners.port.out.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;

public interface UpdateOwnerPort {
    Owner update(Owner owner); // Saves owner and publishes events
}