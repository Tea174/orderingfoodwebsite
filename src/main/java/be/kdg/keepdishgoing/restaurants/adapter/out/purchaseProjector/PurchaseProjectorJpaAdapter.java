package be.kdg.keepdishgoing.restaurants.adapter.out.purchaseProjector;

import be.kdg.keepdishgoing.restaurants.domain.purchaseRecord.PurchaseProjectorRecord;
import be.kdg.keepdishgoing.restaurants.port.out.purchaseProjector.LoadPurchaseProjectorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PurchaseProjectorJpaAdapter implements LoadPurchaseProjectorPort {
    private final PurchaseProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(PurchaseProjectorJpaAdapter.class);

    public PurchaseProjectorJpaAdapter(PurchaseProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(PurchaseProjectorRecord purchaseProjectorRecord) {
        logger.debug("Saving order record {}", purchaseProjectorRecord);
        PurchaseProjectorEntity entity = new PurchaseProjectorEntity();
        entity.setId(UUID.randomUUID());
        entity.setPurchaseId(purchaseProjectorRecord.purchaseId());
        entity.setRestaurantId(purchaseProjectorRecord.restaurantId());
        logger.debug("Saved order record {}", entity);
        repository.save(entity);
    }

    @Override
    public Optional<PurchaseProjectorRecord> findByOrderId(UUID orderId) {
        return repository.findById(orderId)
                .map(entity -> new PurchaseProjectorRecord(
                        entity.getPurchaseId(),
                        entity.getRestaurantId()
                ));
    }
}