package be.kdg.keepdishgoing.deliveries.domain.requestRecord;

import java.util.UUID;

public record RequestProjectorRecord(
        UUID orderId,
        UUID restaurantId,
        UUID customerId,
        String guestName,
        String guestEmail,
        String recipientName,
        Integer recipientPhone,
        String deliveryAddress
){
}
