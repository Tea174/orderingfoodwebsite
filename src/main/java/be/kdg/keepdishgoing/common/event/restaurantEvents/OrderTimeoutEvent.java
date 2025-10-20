package be.kdg.keepdishgoing.common.event.restaurantEvents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

public record OrderTimeoutEvent(
        UUID orderId,
        UUID restaurantId
) {}