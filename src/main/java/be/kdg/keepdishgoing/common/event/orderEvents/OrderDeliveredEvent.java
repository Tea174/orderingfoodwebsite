package be.kdg.keepdishgoing.common.event.orderEvents;

import java.util.UUID;

public record OrderDeliveredEvent(
        String eventId,
        String occurredAt,
        String restaurantId,
        UUID orderId
) {}