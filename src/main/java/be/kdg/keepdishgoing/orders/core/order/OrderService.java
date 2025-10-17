package be.kdg.keepdishgoing.orders.core.order;


import be.kdg.keepdishgoing.common.event.orderEvents.OrderAcceptedEvent;
import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.domain.order.OrderItem;
import be.kdg.keepdishgoing.orders.domain.order.OrderStatus;
import be.kdg.keepdishgoing.orders.port.in.order.*;
import be.kdg.keepdishgoing.orders.port.out.order.LoadOrderPort;
import be.kdg.keepdishgoing.orders.port.out.order.PublishOrderEventsPort;
import be.kdg.keepdishgoing.orders.port.out.order.SaveOrderPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderService implements CancelOrderUseCase,
        GetOrderUseCase, GetOrderItemUseCase,
        UpdateOrderStatusUseCase,
        GetOrdersByRestaurantUseCase,
        GetOrdersByCustomerUseCase,
        AcceptOrderUseCase,
        RejectOrderUseCase,
        GetCustomerOrderHistoryUseCase{
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final PublishOrderEventsPort  publishOrderEventsPort;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void acceptOrder(UUID orderId) {
        logger.info("Accepting order: {}", orderId);
        Order order = loadOrderPort.loadOrder(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.accept();
        saveOrderPort.saveOrder(order);
        OrderAcceptedEvent event = new OrderAcceptedEvent(
                LocalDateTime.now(),
                order.getOrderId().id(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.isGuestOrder() ? order.getGuestName() : "Customer",
                order.isGuestOrder() ? order.getGuestEmail() : null,
                order.getDeliveryAddress(),
                null
        );

        eventPublisher.publishEvent(event);
        logger.info("Order {} accepted and OrderAcceptedEvent published", orderId);
    }


    @Override
    public void rejectOrder(UUID orderId, String reason) {
        logger.info("Rejecting order: {} with reason: {}", orderId, reason);
        Order order = loadOrderPort.loadOrder(OrderId.of(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.reject(reason);
        saveOrderPort.saveOrder(order);
        logger.info("Order {} rejected", orderId);
        // No event to Delivery BC - order was rejected
    }


    @Override
    public List<OrderHistoryItem> getOrderHistory(GetOrderHistoryQuery query) {
        List<Order> orders = loadOrderPort.loadOrdersByCustomer(
                query.customerId(),
                query.page(),
                query.pageSize()
        );

        return orders.stream()
                .map(order -> new OrderHistoryItem(
                        order.getOrderId(),
                        loadRestaurantPort.loadByNameById(order.getRestaurantId()),
                        order.getStatus(),
                        calculateTotal(order.getItems()),
                        order.getCreatedAt()
                ))
                .toList();
    }


    private double calculateTotal(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void cancel(OrderId orderId) {
        Order order = loadOrderPort.loadOrder(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.cancel();
        saveOrderPort.saveOrder(order);
    }

    @Override
    public Order getOrder(OrderId orderId) {
        return loadOrderPort.loadOrder(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Override
    public List<Order> getOrders(UUID restaurantId) {
        return loadOrderPort.loadOrdersByRestaurant(restaurantId);
    }

    @Override
    public void updateStatus(OrderId orderId, OrderStatus newStatus) {
        Order order = loadOrderPort.loadOrder(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.updateStatus(newStatus);
        saveOrderPort.saveOrder(order);
    }

}