package be.kdg.keepdishgoing.restaurants.adapter.in.web.owner;


import be.kdg.keepdishgoing.restaurants.adapter.in.request.owner.LoginOwnerRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.request.owner.RegisterOwnerRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.request.owner.RejectOrderRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.owner.OwnerLoginResponse;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.owner.OwnerRegisteredResponse;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.common.security.KeycloakService;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.GetRestaurantUseCase;
import be.kdg.keepdishgoing.restaurants.port.out.owner.AcceptPurchasePort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.RejectPurchasePort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerController {

    private final RegisterOwnerUseCase registerOwnerUseCase;
    private final GetOwnerUseCase getOwnerUseCase;
    private final KeycloakService keycloakService;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final AcceptPurchasePort acceptPurchasePort;
    private final RejectPurchasePort rejectPurchasePort;
    private final Logger log = LoggerFactory.getLogger(OwnerController.class);

    @PostMapping("/{restaurantId}/orders/{purchaseId}/accept")
    public ResponseEntity<Void> acceptOrder(
            @PathVariable UUID restaurantId,
            @PathVariable UUID purchaseId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyRestaurantOwnership(restaurantId, jwt);
        acceptPurchasePort.acceptOrder(restaurantId, purchaseId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{restaurantId}/orders/{purchaseId}/reject")
    public ResponseEntity<Void> rejectOrder(
            @PathVariable UUID restaurantId,
            @PathVariable UUID purchaseId,
            @RequestBody @Valid RejectOrderRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        verifyRestaurantOwnership(restaurantId, jwt);
        rejectPurchasePort.rejectOrder(restaurantId, purchaseId, request.reason());

        return ResponseEntity.ok().build();
    }
    private void verifyRestaurantOwnership(UUID restaurantId, Jwt jwt) {
        String keycloakId = jwt.getSubject();
        log.info("Verifying ownership: keycloakId={}, requestedRestaurantId={}", keycloakId, restaurantId);

        Restaurant restaurant = getRestaurantUseCase
                .findByOwnerIdKeycloakSubjectId(keycloakId)
                .orElseThrow(() -> new SecurityException("Restaurant not found for owner"));

        log.info("Found restaurant: restaurantId={}", restaurant.getRestaurantId());

        if (!restaurant.getRestaurantId().id().equals(restaurantId)) {
            log.error("Restaurant ID mismatch: expected={}, got={}", restaurant.getRestaurantId(), restaurantId);
            throw new SecurityException("You don't have permission for this restaurant");
        }
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