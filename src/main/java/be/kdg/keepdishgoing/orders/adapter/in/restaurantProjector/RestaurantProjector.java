package be.kdg.keepdishgoing.orders.adapter.in.restaurantProjector;

import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantCreatedEvent;
import be.kdg.keepdishgoing.orders.adapter.out.restaurant.RestaurantProjectorJpaAdapter;
import be.kdg.keepdishgoing.orders.domain.restaurantRecord.RestaurantProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantProjector {
    private final RestaurantProjectorJpaAdapter jpaAdapter;
    private final Logger logger = LoggerFactory.getLogger(RestaurantProjector.class);

    public RestaurantProjector(RestaurantProjectorJpaAdapter jpaAdapter) {
        this.jpaAdapter = jpaAdapter;
    }

    @EventListener
    public void on(RestaurantCreatedEvent event) {
        logger.debug("Creating restaurant event" + event.toString());
        RestaurantProjectorRecord restaurant = new RestaurantProjectorRecord(
                event.restaurantId(),
                event.ownerKeycloakId(),
                event.restaurantName(),
                event.address(),
                event.cuisine(),
                event.estimatedDeliveryTime()
        );
        logger.debug(restaurant.toString());
        jpaAdapter.save(restaurant);
    }
}