package be.kdg.keepdishgoing.restaurants.core.owner;

import be.kdg.keepdishgoing.common.event.restaurantEvents.OrderTimeoutEvent;
import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantAcceptedOrderEvent;
import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantRejectedOrderEvent;
import be.kdg.keepdishgoing.restaurants.core.scheduler.OrderTimeoutScheduler;
import be.kdg.keepdishgoing.restaurants.domain.purchaseRecord.PurchaseProjectorRecord;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.UpdateOwnerProfileUseCase;
import be.kdg.keepdishgoing.restaurants.port.out.purchaseProjector.LoadPurchaseProjectorPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.AcceptPurchasePort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.LoadOwnerPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.RejectPurchasePort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.UpdateOwnerPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import static be.kdg.keepdishgoing.restaurants.core.OrderTimeoutConfig.TIMEOUT_SECONDS;

@Service
@Transactional
@AllArgsConstructor
public class OwnerService implements
        RegisterOwnerUseCase,
        UpdateOwnerProfileUseCase,
        GetOwnerUseCase,
        AcceptPurchasePort,
        RejectPurchasePort {

    private static final Logger log = LoggerFactory.getLogger(OwnerService.class);


    private final UpdateOwnerPort updateOwnerPort;
    private final LoadOwnerPort loadOwnerPort;
    private final LoadPurchaseProjectorPort loadPurchaseProjectorPort;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderTimeoutScheduler orderTimeoutScheduler;

    @Override
    public void acceptOrder(UUID restaurantId, UUID purchaseId) {
        PurchaseProjectorRecord orderRecord = loadPurchaseProjectorPort.findByPurchaseId(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + purchaseId));

        if (!orderRecord.restaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException("Order does not belong to this restaurant");
        }
        // Check if timeout already passed
        Duration timeElapsed = Duration.between(orderRecord.receivedAt(), LocalDateTime.now());
        if (timeElapsed.getSeconds() > TIMEOUT_SECONDS) {
            throw new IllegalStateException(
                    "Cannot accept order - timeout period has expired (" + TIMEOUT_SECONDS + " seconds)"
            );
        }
        // Cancel the scheduled auto-rejection
        orderTimeoutScheduler.cancelOrderTimeout(purchaseId);

        // Publish acceptance event
        RestaurantAcceptedOrderEvent event = new RestaurantAcceptedOrderEvent(
                LocalDateTime.now(),
                purchaseId,
                restaurantId
        );

        eventPublisher.publishEvent(event);
        log.info("Order {} accepted by restaurant {}", purchaseId, restaurantId);
    }

    @Override
    public void rejectOrder(UUID restaurantId, UUID purchaseId, String reason) {
        PurchaseProjectorRecord orderRecord = loadPurchaseProjectorPort.findByPurchaseId(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + purchaseId));

        if (!orderRecord.restaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException("Order does not belong to this restaurant");
        }

        // Cancel the scheduled auto-rejection (if manually rejecting)
        orderTimeoutScheduler.cancelOrderTimeout(purchaseId);

        // Publish rejection event
        RestaurantRejectedOrderEvent event = new RestaurantRejectedOrderEvent(
                LocalDateTime.now(),
                purchaseId,
                restaurantId,
                reason
        );

        eventPublisher.publishEvent(event);
        log.info("Order {} rejected by restaurant {}: {}", purchaseId, restaurantId, reason);
    }

    @EventListener
    public void handleOrderTimeout(OrderTimeoutEvent event) {
        rejectOrder(
                event.restaurantId(),
                event.orderId(),
                "Restaurant did not respond within 15 seconds"
        );
    }


    @Override
    public OwnerId register(RegisterOwnerCommand command) {
        // Check if email already exists
        loadOwnerPort.loadByEmail(command.email()).ifPresent(owner -> {
            throw new IllegalArgumentException("Email already registered");
        });

        // Create owner (ID is auto-generated in createOwner)
        Owner owner = Owner.createOwner(
                command.keycloakSubjectId(),
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

    @Override
    public Owner getOwnerByKeycloakId(String keycloakSubjectId) {
        return loadOwnerPort.findByKeycloakId(keycloakSubjectId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Owner not found for Keycloak ID: " + keycloakSubjectId
                ));
    }


}
