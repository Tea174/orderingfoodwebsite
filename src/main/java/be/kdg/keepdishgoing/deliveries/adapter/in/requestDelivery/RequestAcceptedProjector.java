package be.kdg.keepdishgoing.deliveries.adapter.in.requestDelivery;

import be.kdg.keepdishgoing.common.event.orderEvents.forDelivery.OrderAcceptedEvent;
import be.kdg.keepdishgoing.deliveries.adapter.out.requestProjector.RequestProjectorJpaAdapter;
import be.kdg.keepdishgoing.deliveries.domain.requestRecord.RequestProjectorRecord;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestAcceptedProjector {
    private final RequestProjectorJpaAdapter adapter;
    private static final Logger log = LoggerFactory.getLogger(RequestAcceptedProjector.class);

    @EventListener
    public void on(OrderAcceptedEvent event) {
        log.info("Order Accepted Event received for delivery: {}", event);

        RequestProjectorRecord record = new RequestProjectorRecord(
                event.orderId(),
                event.restaurantId(),
                event.customerId(),
                event.guestName(),
                event.guestEmail(),
                event.recipientName(),           // ALWAYS present
                event.recipientPhone(),          // ALWAYS present
                event.deliveryAddress()

        );

        adapter.save(record);
    }
}