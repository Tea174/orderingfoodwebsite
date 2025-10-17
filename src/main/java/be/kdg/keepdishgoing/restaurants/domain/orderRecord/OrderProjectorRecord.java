package be.kdg.keepdishgoing.restaurants.domain.orderRecord;

import java.util.UUID;

public record OrderProjectorRecord(
        UUID orderId,
        UUID restaurantId
){
}
