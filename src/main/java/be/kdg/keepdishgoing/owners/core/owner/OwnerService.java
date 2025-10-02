package be.kdg.keepdishgoing.owners.core.owner;

import be.kdg.keepdishgoing.owners.domain.Owner;
import be.kdg.keepdishgoing.owners.domain.OwnerId;
import be.kdg.keepdishgoing.owners.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.owners.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.owners.port.in.owner.UpdateOwnerProfileUseCase;
import be.kdg.keepdishgoing.owners.port.out.owner.LoadOwnerPort;
import be.kdg.keepdishgoing.owners.port.out.owner.SaveOwnerPort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class OwnerService implements
        RegisterOwnerUseCase,
        UpdateOwnerProfileUseCase,
        GetOwnerUseCase {

    private final SaveOwnerPort saveOwnerPort;
    private final LoadOwnerPort loadOwnerPort;
    private final PasswordEncoder passwordEncoder;

    public OwnerService(SaveOwnerPort saveOwnerPort,
                        LoadOwnerPort loadOwnerPort,
                        PasswordEncoder passwordEncoder) {
        this.saveOwnerPort = saveOwnerPort;
        this.loadOwnerPort = loadOwnerPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OwnerId register(RegisterOwnerCommand command) {
        // Check if email already exists
        loadOwnerPort.loadByEmail(command.email()).ifPresent(owner -> {
            throw new IllegalArgumentException("Email already registered");
        });

        // Hash the password
        String hashedPassword = passwordEncoder.encode(command.password());

        // Create owner
        Owner owner = Owner.createOwner(
                command.firstName(),
                command.lastName(),
                command.email(),
                hashedPassword,
                command.phoneNumber(),
                command.address()
        );

        Owner savedOwner = saveOwnerPort.save(owner);
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

        saveOwnerPort.save(owner);
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