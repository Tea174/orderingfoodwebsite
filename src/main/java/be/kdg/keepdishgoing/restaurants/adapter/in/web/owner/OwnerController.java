package be.kdg.keepdishgoing.restaurants.adapter.in.web.owner;

import be.kdg.keepdishgoing.restaurants.adapter.in.request.owner.LoginOwnerRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.request.owner.RegisterOwnerRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.owner.OwnerLoginResponse;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.owner.OwnerRegisteredResponse;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.security.KeycloakService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final RegisterOwnerUseCase registerOwnerUseCase;
    private final GetOwnerUseCase getOwnerUseCase;
    private final KeycloakService keycloakService;

    public OwnerController(RegisterOwnerUseCase registerOwnerUseCase, GetOwnerUseCase getOwnerUseCase, KeycloakService keycloakService) {
        this.registerOwnerUseCase = registerOwnerUseCase;
        this.getOwnerUseCase = getOwnerUseCase;
        this.keycloakService = keycloakService;
    }


    @PostMapping("/register")
    public ResponseEntity<OwnerRegisteredResponse> register(
            @Valid @RequestBody RegisterOwnerRequest request) {

        // 1. Create in Keycloak (only email & password) - returns subject ID
        String keycloakSubjectId = keycloakService.createOwner(
                request.email(),
                request.password()  // Only these go to Keycloak
        );

        // 2. Create in PostgreSQL (all business data + keycloak link )
        RegisterOwnerUseCase.RegisterOwnerCommand command = new RegisterOwnerUseCase.RegisterOwnerCommand(
                keycloakSubjectId,  // Link to Keycloak
                request.email(),
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.address()
        );

        OwnerId ownerId = registerOwnerUseCase.register(command);
        Owner owner = getOwnerUseCase.getOwnerById(ownerId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OwnerRegisteredResponse.fromDomain(owner));
    }


    @PostMapping("/login")
    public ResponseEntity<OwnerLoginResponse> login(@Valid @RequestBody LoginOwnerRequest request) {

        // Get JWT from Keycloak
        var tokenResponse = keycloakService.authenticate(request.email(), request.password());

        // verify owner exists in DB
        Owner owner = getOwnerUseCase.getOwnerByEmail(request.email());
        System.out.println(owner);

        return ResponseEntity.ok(new OwnerLoginResponse(
                tokenResponse.accessToken(),
                tokenResponse.refreshToken(),
                tokenResponse.expiresIn()
        ));
    }

}