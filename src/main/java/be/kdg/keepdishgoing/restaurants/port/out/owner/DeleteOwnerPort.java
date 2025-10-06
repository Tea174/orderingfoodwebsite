package be.kdg.keepdishgoing.restaurants.port.out.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;

public interface DeleteOwnerPort {
    void delete(OwnerId ownerId);
}