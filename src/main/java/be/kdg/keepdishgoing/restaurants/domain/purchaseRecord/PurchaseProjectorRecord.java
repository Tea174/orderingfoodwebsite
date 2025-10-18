package be.kdg.keepdishgoing.restaurants.domain.purchaseRecord;

import java.util.UUID;

public record PurchaseProjectorRecord(
        UUID purchaseId,
        UUID restaurantId
){
}
