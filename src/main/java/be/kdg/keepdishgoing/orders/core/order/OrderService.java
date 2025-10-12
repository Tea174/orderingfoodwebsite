package be.kdg.keepdishgoing.orders.core.order;


import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.domain.order.OrderItem;
import be.kdg.keepdishgoing.orders.domain.order.OrderStatus;
import be.kdg.keepdishgoing.orders.port.in.order.*;
import be.kdg.keepdishgoing.orders.port.out.order.LoadOrderPort;
import be.kdg.keepdishgoing.orders.port.out.order.SaveOrderPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.LoadRestaurantPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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
    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final LoadRestaurantPort loadRestaurantPort;


    @Override
    public void acceptOrder(AcceptOrderCommand command) {
        Order order = loadOrderPort.loadOrder(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.accept();
        saveOrderPort.saveOrder(order);
    }

    @Override
    public void rejectOrder(RejectOrderCommand command) {
        Order order = loadOrderPort.loadOrder(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.reject(command.reason());
        saveOrderPort.saveOrder(order);
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