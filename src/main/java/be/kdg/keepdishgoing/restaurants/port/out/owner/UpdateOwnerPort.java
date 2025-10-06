package be.kdg.keepdishgoing.restaurants.port.out.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;

public interface UpdateOwnerPort {
    Owner update(Owner owner); // Saves owner and publishes events
}