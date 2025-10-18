package be.kdg.keepdishgoing.orders.adapter.in.customerProjector;

import be.kdg.keepdishgoing.common.event.customerEvents.CustomerCreatedEvent;
import be.kdg.keepdishgoing.orders.adapter.out.customerProjector.CustomerProjectorJpaAdapter;
import be.kdg.keepdishgoing.orders.domain.customerRecord.CustomerProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerProjector {
    private final CustomerProjectorJpaAdapter jpaAdapter;
    private final Logger logger = LoggerFactory.getLogger(CustomerProjector.class);

    public CustomerProjector(CustomerProjectorJpaAdapter jpaAdapter) {
        this.jpaAdapter = jpaAdapter;
    }

    @EventListener
    public void on(CustomerCreatedEvent event) {
        logger.info("Received CustomerCreatedEvent: {}", event);
        CustomerProjectorRecord record = new CustomerProjectorRecord(
                event.customerId(),
                event.keycloakId(),
                event.firstName(),
                event.lastName(),
                event.email(),
                event.phoneNumber(),
                event.address()
        );
        logger.debug(record.toString());
        jpaAdapter.save(record);
    }
}