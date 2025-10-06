package be.kdg.keepdishgoing.owners.port.out.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;

public interface RegisterOwnerPort {

    Owner SignUp(Owner owner);
}
