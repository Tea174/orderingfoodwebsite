package be.kdg.keepdishgoing.orders.adapter.out.restaurantProjector;

import be.kdg.keepdishgoing.orders.adapter.in.restaurantProjector.RestaurantProjector;
import be.kdg.keepdishgoing.orders.domain.restaurantRecord.RestaurantProjectorRecord;
import be.kdg.keepdishgoing.orders.port.out.restaurantProjector.LoadRestaurantProjectorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantProjectorJpaAdapter implements LoadRestaurantProjectorPort {

    private final RestaurantProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(RestaurantProjectorJpaAdapter.class);

    public RestaurantProjectorJpaAdapter(RestaurantProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(RestaurantProjectorRecord restaurantProjectorRecord) {
        logger.debug("Saving restaurant projector Record");
        RestaurantProjectorEntity entity = new RestaurantProjectorEntity();
        entity.setRestaurantId(restaurantProjectorRecord.restaurantId());
        entity.setOwnerKeycloakSubjectId(restaurantProjectorRecord.ownerKeycloakId());
        entity.setName(restaurantProjectorRecord.name());
        entity.setAddress(restaurantProjectorRecord.address());
        entity.setCuisine(restaurantProjectorRecord.cuisine());
        entity.setPreparationTime(restaurantProjectorRecord.preparationTime());
        logger.debug("Saving restaurant projector Record " + entity);
        repository.save(entity);
    }

    @Override
    public Optional<RestaurantProjectorRecord> findById(UUID restaurantId) {
        logger.debug("Finding restaurant projector Record with id " + restaurantId);
        return repository.findById(restaurantId)
                .map(entity -> new RestaurantProjectorRecord(
                        entity.getRestaurantId(),
                        entity.getOwnerKeycloakSubjectId(),
                        entity.getName(),
                        entity.getAddress(),
                        entity.getCuisine(),
                        entity.getPreparationTime()
                ));
    }
}