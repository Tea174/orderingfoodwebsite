package be.kdg.keepdishgoing.deliveries.adapter.out.requestProjector;

import be.kdg.keepdishgoing.deliveries.domain.requestRecord.RequestProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestProjectorJpaAdapter {
    private final RequestProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(RequestProjectorJpaAdapter.class);


    public RequestProjectorJpaAdapter(RequestProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(RequestProjectorRecord requestProjectorRecord) {
        logger.debug("Saving order record {}", requestProjectorRecord);
        RequestProjectorEntity requestProjectorEntity = new RequestProjectorEntity();
        requestProjectorEntity.setRequestId(requestProjectorRecord.orderId());
        requestProjectorEntity.setRestaurantId(requestProjectorRecord.restaurantId());
        logger.debug("Saving order record {}", requestProjectorEntity);
        repository.save(requestProjectorEntity);


    }


}
