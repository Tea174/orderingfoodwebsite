package be.kdg.keepdishgoing.orders.adapter.out.restaurant;

import be.kdg.keepdishgoing.orders.domain.restaurantRecord.RestaurantProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RestaurantProjectorJpaAdapter {

    private final RestaurantProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(RestaurantProjectorJpaAdapter.class);

    public RestaurantProjectorJpaAdapter(RestaurantProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(RestaurantProjectorRecord restaurantProjectorRecord) {
        logger.debug("Saving restaurant projector Record");
        RestaurantProjectorEntity entity = new RestaurantProjectorEntity();
        entity.setRestaurantId(restaurantProjectorRecord.restaurantId());
        entity.setOwnerKeycloakId(restaurantProjectorRecord.ownerKeycloakId());
        entity.setName(restaurantProjectorRecord.name());
        entity.setAddress(restaurantProjectorRecord.address());
        entity.setCuisine(restaurantProjectorRecord.cuisine());
        entity.setEstimatedDeliveryTime(restaurantProjectorRecord.estimatedDeliveryTime());
        logger.debug("Saving restaurant projector Record " + entity);
        repository.save(entity);
    }
}