package be.kdg.keepdishgoing.deliveries.adapter.out.delivery;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "delivery.exchange";
    public static final String QUEUE_NAME = "restaurant.queue";

    public static final String ORDER_ACCEPTED_KEY = "order.accepted";
    public static final String ORDER_READY_KEY = "order.ready";
    public static final String ORDER_PICKED_UP_KEY = "order.pickedup";
    public static final String ORDER_LOCATION_KEY = "order.location";
    public static final String ORDER_DELIVERED_KEY = "order.delivered";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding bindingPickedUp(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_PICKED_UP_KEY);
    }

    @Bean
    public Binding bindingLocation(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_LOCATION_KEY);
    }

    @Bean
    public Binding bindingDelivered(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_DELIVERED_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}