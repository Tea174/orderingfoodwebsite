package be.kdg.keepdishgoing.restaurants.core.owner;

import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantAcceptedOrderEvent;
import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantRejectedOrderEvent;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.restaurants.adapter.out.orderProjector.OrderProjectorJpaAdapter;
import be.kdg.keepdishgoing.restaurants.domain.orderRecord.OrderProjectorRecord;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.RegisterOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.UpdateOwnerProfileUseCase;
import be.kdg.keepdishgoing.restaurants.port.out.orderProjector.LoadOrderProjectorPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.AcceptOrderPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.LoadOwnerPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.RejectOrderPort;
import be.kdg.keepdishgoing.restaurants.port.out.owner.UpdateOwnerPort;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class OwnerService implements
        RegisterOwnerUseCase,
        UpdateOwnerProfileUseCase,
        GetOwnerUseCase,
        AcceptOrderPort,
        RejectOrderPort

{

    private final UpdateOwnerPort updateOwnerPort;
    private final LoadOwnerPort loadOwnerPort;
    private final LoadOrderProjectorPort loadOrderProjectorPort;
    private final ApplicationEventPublisher eventPublisher;


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


    @Override
    public void acceptOrder(UUID restaurantId, OrderId orderId) {
        // Verify order exists and belongs to this restaurant
        OrderProjectorRecord orderRecord = loadOrderProjectorPort.findByOrderId(orderId.id())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId.id()));

        if (!orderRecord.restaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException("Order does not belong to this restaurant");
        }

        // Restaurant BC just publishes that it accepted the order
        RestaurantAcceptedOrderEvent event = new RestaurantAcceptedOrderEvent(
                LocalDateTime.now(),
                orderId.id(),
                restaurantId
        );

        eventPublisher.publishEvent(event);
    }

    @Override
    public void rejectOrder(UUID restaurantId, UUID orderId, String reason) {
        // Verify order exists and belongs to this restaurant
        OrderProjectorRecord orderRecord = loadOrderProjectorPort.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        if (!orderRecord.restaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException("Order does not belong to this restaurant");
        }

        // Restaurant BC just publishes that it rejected the order
        RestaurantRejectedOrderEvent event = new RestaurantRejectedOrderEvent(
                LocalDateTime.now(),
                orderId,
                restaurantId,
                reason
        );

        eventPublisher.publishEvent(event);
    }
}
