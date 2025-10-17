package be.kdg.keepdishgoing.delivery.adapter.in.delivery;

import be.kdg.keepdishgoing.common.event.orderEvents.OrderDeliveredEvent;
import be.kdg.keepdishgoing.common.event.orderEvents.OrderPickedUpEvent;
import be.kdg.keepdishgoing.delivery.adapter.out.delivery.RabbitMQConfig;
import be.kdg.keepdishgoing.delivery.port.in.DeliveryUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryEventListener {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryEventListener.class);
    private final DeliveryUseCase deliveryUseCase;
    private final ObjectMapper objectMapper;

    /**
     * Listens to RabbitMQ messages from external delivery service
     * and updates delivery status accordingly
     */
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleDeliveryEvents(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();

        try {
            switch (routingKey) {
                case RabbitMQConfig.ORDER_PICKED_UP_KEY -> {
                    OrderPickedUpEvent event = convertMessage(message, OrderPickedUpEvent.class);
                    deliveryUseCase.handleOrderPickedUp(event.orderId());
                    logger.info("✅ Order picked up: {}", event.orderId());
                }
                case RabbitMQConfig.ORDER_DELIVERED_KEY -> {
                    OrderDeliveredEvent event = convertMessage(message, OrderDeliveredEvent.class);
                    deliveryUseCase.handleOrderDelivered(event.orderId());
                    logger.info("✅ Order delivered: {}", event.orderId());
                }
                default -> logger.debug("Ignoring event with routing key: {}", routingKey);
            }
        } catch (Exception e) {
            logger.error("❌ Error processing delivery event with routing key {}: {}",
                    routingKey, e.getMessage(), e);
        }
    }

    private <T> T convertMessage(Message message, Class<T> clazz) throws Exception {
        String json = new String(message.getBody());
        return objectMapper.readValue(json, clazz);
    }
}