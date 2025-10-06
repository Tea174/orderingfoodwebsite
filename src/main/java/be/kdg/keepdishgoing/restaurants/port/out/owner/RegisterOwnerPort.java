package be.kdg.keepdishgoing.restaurants.port.out.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;

public interface RegisterOwnerPort {

    Owner SignUp(Owner owner);
}
