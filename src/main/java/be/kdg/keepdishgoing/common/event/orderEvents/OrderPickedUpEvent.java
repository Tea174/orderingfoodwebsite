package be.kdg.keepdishgoing.common.event.orderEvents;

import java.util.UUID;

public record OrderPickedUpEvent(
        String eventId,
        String occurredAt,
        UUID restaurantId,
        UUID orderId
) {}