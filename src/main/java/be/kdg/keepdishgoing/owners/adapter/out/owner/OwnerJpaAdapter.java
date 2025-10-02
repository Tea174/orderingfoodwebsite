package be.kdg.keepdishgoing.owners.adapter.out.owner;

import be.kdg.keepdishgoing.owners.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.owners.domain.Owner;
import be.kdg.keepdishgoing.owners.domain.OwnerId;
import be.kdg.keepdishgoing.owners.port.out.owner.DeleteOwnerPort;
import be.kdg.keepdishgoing.owners.port.out.owner.LoadOwnerPort;
import be.kdg.keepdishgoing.owners.port.out.owner.SaveOwnerPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OwnerJpaAdapter implements LoadOwnerPort, SaveOwnerPort, DeleteOwnerPort {

    private final OwnerJpaRepository ownerJpaRepository;
    private final Mapper mapper;

    public OwnerJpaAdapter(OwnerJpaRepository ownerJpaRepository, Mapper mapper) {
        this.ownerJpaRepository = ownerJpaRepository;
        this.mapper = mapper;
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
    public Owner save(Owner owner) {
        OwnerJpaEntity entity = mapper.toEntityOwner(owner);
        OwnerJpaEntity savedEntity = ownerJpaRepository.save(entity);
        return mapper.toDomainOwner(savedEntity);
    }

    @Override
    public void delete(OwnerId ownerId) {
        ownerJpaRepository.deleteById(ownerId.id());
    }
}
