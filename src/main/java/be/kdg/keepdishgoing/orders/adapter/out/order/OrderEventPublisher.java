package be.kdg.keepdishgoing.orders.adapter.out.order;

import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.port.out.order.PublishOrderEventsPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements PublishOrderEventsPort {
    private final ApplicationEventPublisher eventPublisher;

    public OrderEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishEvents(Order order) {
        order.getDomainEvents().forEach(eventPublisher::publishEvent);
        order.clearDomainEvents();
    }
}
