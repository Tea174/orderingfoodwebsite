package be.kdg.keepdishgoing.restaurants.core.owner;

import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.UpdateOwnerProfileUseCase;
import be.kdg.keepdishgoing.restaurants.port.out.owner.LoadOwnerPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.UpdateOwnerPort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class OwnerService implements
        RegisterOwnerUseCase,
        UpdateOwnerProfileUseCase,
        GetOwnerUseCase {

    private final UpdateOwnerPort updateOwnerPort;
    private final LoadOwnerPort loadOwnerPort;


    public OwnerService(UpdateOwnerPort updateOwnerPort,
                        LoadOwnerPort loadOwnerPort)             {
        this.updateOwnerPort = updateOwnerPort;
        this.loadOwnerPort = loadOwnerPort;

    }

    @Override
    public OwnerId register(RegisterOwnerCommand command) {
        // Check if email already exists
        loadOwnerPort.loadByEmail(command.email()).ifPresent(owner -> {
            throw new IllegalArgumentException("Email already registered");
        });

        // Create owner (ID is auto-generated in createOwner)
        Owner owner = Owner.createOwner(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phoneNumber(),
                command.address()
        );
        // Save and publish events
        Owner savedOwner = updateOwnerPort.update(owner);
        return savedOwner.getOwnerId();
    }

    @Override
    public void updateProfile(UpdateProfileCommand command) {
        Owner owner = loadOwnerPort.loadById(command.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        owner.updateProfile(
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.address()
        );

        updateOwnerPort.update(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getOwnerById(OwnerId ownerId) {
        return loadOwnerPort.loadById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getOwnerByEmail(String email) {
        return loadOwnerPort.loadByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }
}