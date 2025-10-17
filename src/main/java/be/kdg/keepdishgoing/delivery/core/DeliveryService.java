package be.kdg.keepdishgoing.delivery.core;

import be.kdg.keepdishgoing.delivery.domain.delivery.Delivery;
import be.kdg.keepdishgoing.delivery.domain.delivery.DeliveryStatus;
import be.kdg.keepdishgoing.delivery.port.in.DeliveryUseCase;
import be.kdg.keepdishgoing.delivery.port.out.LoadDeliveryPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeliveryService implements DeliveryUseCase {

    private final Logger logger = LoggerFactory.getLogger(DeliveryService.class);
    private final LoadDeliveryPort loadDeliveryPort;

    @Override
    public Delivery createDeliveryForCustomer(UUID orderId, UUID customerId, UUID restaurantId,
                                              String deliveryAddress, int preparationTime) {
        logger.info("Creating delivery for customer order: {}", orderId);

        Delivery delivery = Delivery.forCustomer(orderId, customerId, restaurantId, deliveryAddress);

        // Calculate busyness factor
        int busynessFactor = calculateBusynessFactor(restaurantId);

        // Fixed delivery time: 20 minutes
        int deliveryTimeMinutes = 20;

        // Calculate estimated delivery time
        delivery.calculateEstimatedDeliveryTime(deliveryTimeMinutes, preparationTime, busynessFactor);

        return loadDeliveryPort.save(delivery);
    }

    @Override
    public Delivery createDeliveryForGuest(UUID orderId, UUID restaurantId, String guestName,
                                           String guestEmail, String deliveryAddress, int preparationTime) {
        logger.info("Creating delivery for guest order: {}", orderId);

        Delivery delivery = Delivery.forGuest(orderId, restaurantId, guestName, guestEmail, deliveryAddress);

        // Calculate busyness factor
        int busynessFactor = calculateBusynessFactor(restaurantId);

        // Fixed delivery time: 20 minutes
        int deliveryTimeMinutes = 20;

        // Calculate estimated delivery time
        delivery.calculateEstimatedDeliveryTime(deliveryTimeMinutes, preparationTime, busynessFactor);

        return loadDeliveryPort.save(delivery);
    }

    @Override
    public Delivery getDeliveryByOrderId(UUID orderId) {
        logger.info("Getting delivery for order: {}", orderId);
        return loadDeliveryPort.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Delivery not found for order: " + orderId));
    }

    @Override
    public void handleOrderPickedUp(UUID orderId) {
        logger.info("Handling order picked up for: {}", orderId);
        Delivery delivery = getDeliveryByOrderId(orderId);
        delivery.markAsPickedUp();
        loadDeliveryPort.save(delivery);
    }

    @Override
    public void handleOrderDelivered(UUID orderId) {
        logger.info("Handling order delivered for: {}", orderId);
        Delivery delivery = getDeliveryByOrderId(orderId);
        delivery.markAsDelivered();
        loadDeliveryPort.save(delivery);
    }

    @Override
    public void handleOrderCancelled(UUID orderId, String reason) {
        logger.info("Handling order cancelled for: {}", orderId);
        Delivery delivery = getDeliveryByOrderId(orderId);
        delivery.markAsCancelled(reason);
        loadDeliveryPort.save(delivery);
    }

    @Override
    public int calculateBusynessFactor(UUID restaurantId) {
        long pendingOrders = loadDeliveryPort.countByRestaurantIdAndStatus(
                restaurantId,
                DeliveryStatus.PENDING
        );
        // Busyness factor: minimum 1, increases with pending orders
        return (int) Math.max(1, pendingOrders);
    }
}