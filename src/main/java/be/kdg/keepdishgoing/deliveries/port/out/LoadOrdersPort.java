package be.kdg.keepdishgoing.deliveries.port.out;

import jakarta.persistence.criteria.Order;

import java.util.Optional;

public interface LoadOrdersPort {

    Optional<Order> findOrderById(String orderId);
}
