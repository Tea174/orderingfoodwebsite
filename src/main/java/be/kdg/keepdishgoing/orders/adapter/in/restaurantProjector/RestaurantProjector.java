package be.kdg.keepdishgoing.orders.adapter.in.restaurantProjector;


import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantAcceptedOrderEvent;
import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantCreatedEvent;
import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantRejectedOrderEvent;
import be.kdg.keepdishgoing.orders.adapter.out.restaurantProjector.RestaurantProjectorJpaAdapter;
import be.kdg.keepdishgoing.orders.domain.restaurantRecord.RestaurantProjectorRecord;
import be.kdg.keepdishgoing.orders.port.in.order.AcceptOrderUseCase;
import be.kdg.keepdishgoing.orders.port.in.order.RejectOrderUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestaurantProjector {
    private final RestaurantProjectorJpaAdapter jpaAdapter;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final Logger logger = LoggerFactory.getLogger(RestaurantProjector.class);

    @EventListener
    public void on(RestaurantCreatedEvent event) {
        logger.debug("Creating restaurant event{}", event.toString());
        RestaurantProjectorRecord restaurant = new RestaurantProjectorRecord(
                event.restaurantId(),
                event.ownerKeycloakId(),
                event.restaurantName(),
                event.address(),
                event.cuisine(),
                event.preparationTime()
        );
        logger.debug(restaurant.toString());
        logger.info("Creating restaurant event{}", event);
        jpaAdapter.save(restaurant);
    }

    @EventListener
    public void handleRestaurantAcceptedOrder(RestaurantAcceptedOrderEvent event) {
        logger.info("Order {} accepted by restaurant {}", event.orderId(), event.restaurantId());
        acceptOrderUseCase.acceptOrder(event.orderId());
    }

    @EventListener
    public void handleRestaurantRejectedOrder(RestaurantRejectedOrderEvent event) {
        logger.info("Order {} rejected by restaurant {}: {}",
                event.orderId(), event.restaurantId(), event.rejectionReason());
        rejectOrderUseCase.rejectOrder(event.orderId(), event.rejectionReason());
    }

}