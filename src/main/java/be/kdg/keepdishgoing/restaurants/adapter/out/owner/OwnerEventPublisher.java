package be.kdg.keepdishgoing.restaurants.adapter.out.owner;

import be.kdg.keepdishgoing.restaurants.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.port.out.owner.UpdateOwnerPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OwnerEventPublisher implements UpdateOwnerPort {

    private final OwnerJpaRepository ownerJpaRepository;
    private final Mapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public OwnerEventPublisher(OwnerJpaRepository ownerJpaRepository,
                               Mapper mapper,
                               ApplicationEventPublisher eventPublisher) {
        this.ownerJpaRepository = ownerJpaRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Owner update(Owner owner) {
        var ownerEntity = mapper.toEntityOwner(owner);
        var savedEntity = ownerJpaRepository.save(ownerEntity);

        owner.getDomainEvents().forEach(eventPublisher::publishEvent);
//        owner.clearDomainEvents();

        return mapper.toDomainOwner(savedEntity);
    }
}