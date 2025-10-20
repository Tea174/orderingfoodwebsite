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
        logger.debug("Saving request record {}", requestProjectorRecord);

        RequestProjectorEntity entity = new RequestProjectorEntity();
        entity.setRequestId(requestProjectorRecord.orderId());
        entity.setRestaurantId(requestProjectorRecord.restaurantId());
        entity.setCustomerId(requestProjectorRecord.customerId());
        entity.setGuestName(requestProjectorRecord.guestName());
        entity.setGuestEmail(requestProjectorRecord.guestEmail());
        entity.setRecipientName(requestProjectorRecord.recipientName());
        entity.setRecipientPhone(requestProjectorRecord.recipientPhone());
        entity.setDeliveryAddress(requestProjectorRecord.deliveryAddress());

        repository.save(entity);
        logger.debug("Saved request record {}", entity);
    }


}
