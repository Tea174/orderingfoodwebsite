package be.kdg.keepdishgoing.restaurants.adapter.out.purchaseProjector;

import be.kdg.keepdishgoing.restaurants.domain.purchaseRecord.PurchaseProjectorRecord;
import be.kdg.keepdishgoing.restaurants.port.out.purchaseProjector.LoadPurchaseProjectorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class PurchaseProjectorJpaAdapter implements LoadPurchaseProjectorPort {
    private final PurchaseProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(PurchaseProjectorJpaAdapter.class);
    private PurchaseProjectorRecord purchaseProjectorRecord;

    public PurchaseProjectorJpaAdapter(PurchaseProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(PurchaseProjectorRecord purchaseProjectorRecord) {
        this.purchaseProjectorRecord = purchaseProjectorRecord;
        logger.debug("Saving order record {}", purchaseProjectorRecord);
        PurchaseProjectorEntity entity = new PurchaseProjectorEntity();
        entity.setPurchaseId(purchaseProjectorRecord.purchaseId());
        entity.setRestaurantId(purchaseProjectorRecord.restaurantId());
        entity.setReceivedAt(purchaseProjectorRecord.receivedAt());
        logger.debug("Saved order record {}", entity);
        repository.save(entity);
    }

    @Override
    public Optional<PurchaseProjectorRecord> findByPurchaseId(UUID purchaseId) {
        return repository.findById(purchaseId)
                .map(entity -> new PurchaseProjectorRecord(
                        LocalDateTime.now(),
                        entity.getPurchaseId(),
                        entity.getRestaurantId()

                ));
    }
}