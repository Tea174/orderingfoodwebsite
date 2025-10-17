package be.kdg.keepdishgoing.delivery.adapter.in.order;

import be.kdg.keepdishgoing.common.event.orderEvents.OrderCreatedEvent;
import be.kdg.keepdishgoing.delivery.adapter.out.orderProjector.OrderProjectorJpaAdapter;
import be.kdg.keepdishgoing.delivery.domain.orderRecord.OrderProjectorRecord;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderProjector {
    private final OrderProjectorJpaAdapter adapter;
    private static final Logger log = LoggerFactory.getLogger(OrderProjector.class);

    @EventListener
    public void on(OrderCreatedEvent event) {
        log.info("Order Created Event: {}", event);
        OrderProjectorRecord record = new OrderProjectorRecord(
                event.orderId(),
                event.restaurantId(),
                event.customerID
        );
        log.debug(record.toString());
        adapter.save(record);
    }

}
