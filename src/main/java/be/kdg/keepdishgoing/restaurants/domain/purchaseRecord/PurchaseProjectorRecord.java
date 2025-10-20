package be.kdg.keepdishgoing.restaurants.domain.purchaseRecord;

import java.time.LocalDateTime;
import java.util.UUID;

public record PurchaseProjectorRecord(
        LocalDateTime receivedAt,
        UUID purchaseId,
        UUID restaurantId

){
}
