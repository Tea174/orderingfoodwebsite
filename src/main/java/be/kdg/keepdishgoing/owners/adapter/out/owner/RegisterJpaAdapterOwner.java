package be.kdg.keepdishgoing.owners.adapter.out.owner;

import be.kdg.keepdishgoing.owners.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.owners.domain.owner.Owner;
import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;
import be.kdg.keepdishgoing.owners.port.out.owner.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegisterJpaAdapterOwner implements LoadOwnerPort, DeleteOwnerPort, RegisterOwnerPort {

    private final OwnerJpaRepository ownerJpaRepository;
    private final Mapper mapper;

    public RegisterJpaAdapterOwner(OwnerJpaRepository ownerJpaRepository, Mapper mapper) {
        this.ownerJpaRepository = ownerJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Owner SignUp(Owner owner) {
        OwnerJpaEntity entity = mapper.toEntityOwner(owner);
        OwnerJpaEntity savedEntity = ownerJpaRepository.save(entity);
        return mapper.toDomainOwner(savedEntity);
    }

    @Override
    public Optional<Owner> loadById(OwnerId ownerId) {
        return ownerJpaRepository.findById(ownerId.id())
                .map(mapper::toDomainOwner);
    }

    @Override
    public Optional<Owner> loadByEmail(String email) {
        return ownerJpaRepository.findByEmail(email)
                .map(mapper::toDomainOwner);
    }

    @Override
    public void delete(OwnerId ownerId) {
        ownerJpaRepository.deleteById(ownerId.id());
    }

}
