package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.Order;

import java.util.List;
import java.util.UUID;

public interface GetOrdersByRestaurantUseCase {
    List<Order> getOrders(UUID restaurantId);
}
