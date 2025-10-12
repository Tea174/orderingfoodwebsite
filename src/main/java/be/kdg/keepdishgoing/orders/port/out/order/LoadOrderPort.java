package be.kdg.keepdishgoing.orders.port.out.order;

import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadOrderPort {
    Optional<Order> loadOrder(OrderId orderId);
    List<Order> loadOrdersByCustomer(UUID customerId, int page, int pageSize);
    List<Order> loadOrdersByRestaurant(UUID restaurantId);
    List<Order> loadPendingOrdersByRestaurant(UUID restaurantId);
}
