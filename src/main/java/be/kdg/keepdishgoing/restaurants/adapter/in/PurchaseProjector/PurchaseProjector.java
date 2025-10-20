package be.kdg.keepdishgoing.restaurants.adapter.in.PurchaseProjector;

import be.kdg.keepdishgoing.common.event.orderEvents.forRestaurants.OrderCreatedEvent;
import be.kdg.keepdishgoing.restaurants.adapter.out.purchaseProjector.PurchaseProjectorJpaAdapter;
import be.kdg.keepdishgoing.restaurants.core.scheduler.OrderTimeoutScheduler;
import be.kdg.keepdishgoing.restaurants.domain.purchaseRecord.PurchaseProjectorRecord;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class PurchaseProjector {

    private static final Logger log = LoggerFactory.getLogger(PurchaseProjector.class);

    private final OrderTimeoutScheduler orderTimeoutScheduler;
    private final PurchaseProjectorJpaAdapter purchaseProjectorJpaAdapter;

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Restaurant BC received OrderCreatedEvent for order {}", event.orderId());

        PurchaseProjectorRecord record = new PurchaseProjectorRecord(
                LocalDateTime.now(),
                event.orderId(),
                event.restaurantId()

        );
        purchaseProjectorJpaAdapter.save(record);
        orderTimeoutScheduler.scheduleOrderTimeout(event.orderId(), event.restaurantId());
        log.info("Scheduled auto-rejection for order {} in 15 seconds", event.orderId());
    }
}