package be.kdg.keepdishgoing.owners.port.out.owner;

import be.kdg.keepdishgoing.owners.domain.Owner;

public interface SaveOwnerPort {
    Owner save(Owner owner);
}