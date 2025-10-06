package be.kdg.keepdishgoing.owners.adapter.in.web.owner;

import be.kdg.keepdishgoing.owners.adapter.in.request.owner.OwnerResponse;
import be.kdg.keepdishgoing.owners.adapter.in.request.owner.RegisterOwnerRequest;
import be.kdg.keepdishgoing.owners.adapter.in.response.owner.OwnerRegisteredResponse;
import be.kdg.keepdishgoing.owners.domain.owner.Owner;
import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;
import be.kdg.keepdishgoing.owners.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.owners.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.security.KeycloakService;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    // PROTECTED - Requires JWT, gets current user info from token
    @GetMapping("/me")
    public ResponseEntity<OwnerResponse> getCurrentOwner(
            @AuthenticationPrincipal Jwt token) {

        // Extract email from JWT (Keycloak standard claim)
        String email = token.getClaimAsString(StandardClaimNames.EMAIL);

        // Or extract subject ID (Keycloak user ID)
        String keycloakUserId = token.getSubject();

        // Find owner by email
        Owner owner = getOwnerUseCase.getOwnerByEmail(email);

        return ResponseEntity.ok(OwnerResponse.fromDomain(owner));
    }

    // PROTECTED - Requires JWT and 'owner' role
    @GetMapping("/{ownerId}")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OwnerResponse> getOwnerById(
            @PathVariable UUID ownerId,
            @AuthenticationPrincipal Jwt token) {

        // You can verify the user is accessing their own data
        String tokenEmail = token.getClaimAsString(StandardClaimNames.EMAIL);
        Owner requestedOwner = getOwnerUseCase.getOwnerById(OwnerId.of(ownerId));

        if (!requestedOwner.getEmail().equals(tokenEmail)) {
            throw new ForbiddenException("You can only access your own data");
        }

        return ResponseEntity.ok(OwnerResponse.fromDomain(requestedOwner));
    }

    // PROTECTED - Only accessible to users with 'owner' role
//    @PutMapping("/{ownerId}")
//    @PreAuthorize("hasAuthority('owner')")
//    public ResponseEntity<OwnerResponse> updateOwner(
//            @PathVariable UUID ownerId,
//            @Valid @RequestBody UpdateOwnerRequest request,
//            @AuthenticationPrincipal Jwt token) {
//
//        // Update owner logic...
//        return ResponseEntity.ok(updatedOwner);
//    }
}