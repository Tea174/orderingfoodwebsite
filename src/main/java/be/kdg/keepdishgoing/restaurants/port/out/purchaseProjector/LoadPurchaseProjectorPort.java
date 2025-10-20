package be.kdg.keepdishgoing.restaurants.port.out.purchaseProjector;

import be.kdg.keepdishgoing.restaurants.domain.purchaseRecord.PurchaseProjectorRecord;

import java.util.Optional;
import java.util.UUID;

public interface LoadPurchaseProjectorPort {
    Optional<PurchaseProjectorRecord> findByPurchaseId(UUID purchaseId) ;
}