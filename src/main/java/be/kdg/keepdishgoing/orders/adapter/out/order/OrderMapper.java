package be.kdg.keepdishgoing.orders.adapter.out.order;

import be.kdg.keepdishgoing.orders.adapter.out.order.orderItem.OrderItemJpaEntity;
import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.domain.order.OrderItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderJpaEntity toJpaEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setOrderId(order.getOrderId().id());
        entity.setCustomerId(order.getCustomerId());  // can be null
        entity.setGuestName(order.getGuestName());
        entity.setGuestEmail(order.getGuestEmail());
        entity.setDeliveryAddress(order.getDeliveryAddress());
        entity.setRestaurantId(order.getRestaurantId());
        entity.setTotalPrice(order.getTotalPrice());
        entity.setStatus(order.getStatus());
        entity.setCreatedAt(LocalDateTime.now());

        entity.setItems(order.getItems().stream()
                .map(item -> toOrderItemJpaEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    public Order toDomain(OrderJpaEntity entity) {
        Order order = new Order();
        order.setOrderId(new OrderId(entity.getOrderId()));
        order.setCustomerId(entity.getCustomerId());
        order.setGuestName(entity.getGuestName());
        order.setGuestEmail(entity.getGuestEmail());
        order.setDeliveryAddress(entity.getDeliveryAddress());
        order.setRestaurantId(entity.getRestaurantId());
        order.setTotalPrice(entity.getTotalPrice());
        order.setStatus(entity.getStatus());
        order.setCreatedAt(entity.getCreatedAt());
        order.setItems(entity.getItems().stream()
                .map(this::toOrderItem)
                .collect(Collectors.toList()));

        return order;
    }

    private OrderItemJpaEntity toOrderItemJpaEntity(OrderItem item, OrderJpaEntity order) {
        OrderItemJpaEntity entity = new OrderItemJpaEntity();
        entity.setOrder(order);
        entity.setDishId(item.getDishId());
        entity.setDishName(item.getDishName());
        entity.setPrice(item.getPrice());
        entity.setQuantity(item.getQuantity());
        return entity;
    }

    private OrderItem toOrderItem(OrderItemJpaEntity entity) {
        return new OrderItem(
                entity.getDishId(),
                entity.getDishName(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }
}
