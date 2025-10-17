package be.kdg.keepdishgoing.delivery.domain.orderRecord;

import java.util.UUID;

public record OrderProjectorRecord (
        UUID orderId,
        UUID restaurantId,
        UUID customerID
){
}
