package be.kdg.keepdishgoing.deliveries.domain.requestRecord;

import java.util.UUID;

public record RequestProjectorRecord(
        UUID orderId,
        UUID restaurantId,
        UUID customerId,
        Integer preparationTime,
        String guestName,
        String guestEmail,
        String recipientName,
        String recipientPhone,
        String deliveryAddress
){
}
