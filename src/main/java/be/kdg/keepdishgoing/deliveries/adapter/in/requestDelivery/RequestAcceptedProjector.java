package be.kdg.keepdishgoing.deliveries.adapter.in.requestDelivery;

import be.kdg.keepdishgoing.common.event.orderEvents.forDelivery.OrderAcceptedEvent;
import be.kdg.keepdishgoing.deliveries.adapter.out.requestProjector.RequestProjectorJpaAdapter;
import be.kdg.keepdishgoing.deliveries.domain.requestRecord.RequestProjectorRecord;
import be.kdg.keepdishgoing.deliveries.port.in.DeliveryUseCase;
import be.kdg.keepdishgoing.deliveries.domain.delivery.Delivery;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestAcceptedProjector {
    private final RequestProjectorJpaAdapter adapter;
    private final DeliveryUseCase deliveryUseCase;
    private static final Logger log = LoggerFactory.getLogger(RequestAcceptedProjector.class);

    @EventListener
    public void on(OrderAcceptedEvent event) {
        log.info("Order Accepted Event received for delivery: {}", event);

        // 1. Save request projector record
        RequestProjectorRecord record = new RequestProjectorRecord(
                event.orderId(),
                event.restaurantId(),
                event.customerId(),
                event.preparationTimeMinutes(),
                event.guestName(),
                event.guestEmail(),
                event.recipientName(),
                event.recipientPhone(),
                event.deliveryAddress()
        );
        adapter.save(record);

        // 2. Get preparation time from event
        int preparationTime = event.preparationTimeMinutes();
        log.info("Restaurant preparation time: {} minutes", preparationTime);

        // 3. Create delivery with preparation time
        Delivery delivery;
        if (event.isGuestOrder()) {
            log.info("Creating delivery for guest order: {}", event.orderId());
            delivery = deliveryUseCase.createDeliveryForGuest(
                    event.orderId(),
                    event.restaurantId(),
                    event.guestName(),
                    event.guestEmail(),
                    event.deliveryAddress(),
                    preparationTime
            );
        } else {
            log.info("Creating delivery for customer order: {}", event.orderId());
            delivery = deliveryUseCase.createDeliveryForCustomer(
                    event.orderId(),
                    event.customerId(),
                    event.restaurantId(),
                    event.deliveryAddress(),
                    preparationTime  //  Pass to delivery service
            );
        }

        // 4. Immediately mark as PICKED_UP
        log.info("Marking delivery as PICKED_UP for order: {}", event.orderId());
        deliveryUseCase.handleOrderPickedUp(event.orderId());

        // 5. Log the estimated delivery time
        log.info("Delivery created and marked as PICKED_UP for order: {}. Estimated delivery time: {}",
                event.orderId(), delivery.getEstimatedDeliveryTime());
    }
}