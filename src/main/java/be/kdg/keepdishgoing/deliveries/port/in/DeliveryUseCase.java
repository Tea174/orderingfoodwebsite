package be.kdg.keepdishgoing.deliveries.port.in;

import be.kdg.keepdishgoing.deliveries.domain.delivery.Delivery;

import java.util.UUID;

public interface DeliveryUseCase {
    Delivery createDeliveryForCustomer(UUID orderId, UUID customerId, UUID restaurantId,
                                       String deliveryAddress, int preparationTime);

    Delivery createDeliveryForGuest(UUID orderId, UUID restaurantId, String guestName,
                                    String guestEmail, String deliveryAddress, int preparationTime);

    Delivery getDeliveryByOrderId(UUID orderId);

    void handleOrderPickedUp(UUID orderId);

    void handleOrderDelivered(UUID orderId);

    void handleOrderCancelled(UUID orderId, String reason);

    int calculateBusynessFactor(UUID restaurantId);

}