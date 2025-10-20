package be.kdg.keepdishgoing.restaurants.core.scheduler;

import be.kdg.keepdishgoing.common.event.restaurantEvents.OrderTimeoutEvent;
import be.kdg.keepdishgoing.restaurants.core.OrderTimeoutConfig;
import be.kdg.keepdishgoing.restaurants.port.out.owner.RejectPurchasePort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import static be.kdg.keepdishgoing.restaurants.core.OrderTimeoutConfig.TIMEOUT_SECONDS;

@Component
@AllArgsConstructor
public class OrderTimeoutScheduler {
    private static final Logger log = LoggerFactory.getLogger(OrderTimeoutScheduler.class);

    private final TaskScheduler taskScheduler;
    private final ApplicationEventPublisher eventPublisher;

    private final Map<UUID, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void scheduleOrderTimeout(UUID orderId, UUID restaurantId) {
        log.info("Scheduling auto-rejection for order {} in {} seconds", orderId, TIMEOUT_SECONDS);

        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> autoRejectOrder(orderId, restaurantId),
                Instant.now().plusSeconds(TIMEOUT_SECONDS)
        );

        scheduledTasks.put(orderId, future);
    }

    public void cancelOrderTimeout(UUID orderId) {
        ScheduledFuture<?> future = scheduledTasks.remove(orderId);
        if (future != null && !future.isDone()) {
            future.cancel(false);
            log.info("Cancelled auto-rejection for order {}", orderId);
        }
    }

    private void autoRejectOrder(UUID orderId, UUID restaurantId) {
        try {
            log.warn("Auto-rejecting order {} due to timeout", orderId);

            eventPublisher.publishEvent(new OrderTimeoutEvent(orderId, restaurantId));

            scheduledTasks.remove(orderId);
        } catch (Exception e) {
            log.error("Error auto-rejecting order {}: {}", orderId, e.getMessage());
        }
    }
}