package be.kdg.keepdishgoing.restaurants.port.in.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;

public interface GetOwnerUseCase {
    Owner getOwnerById(OwnerId ownerId);
    Owner getOwnerByEmail(String email);
    Owner getOwnerByKeycloakId(String keycloakSubjectId);
}
