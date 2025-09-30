package be.kdg.keepdishgoing.customers.port.out;

import jakarta.persistence.criteria.Order;

import java.util.Optional;

public interface LoadOrderPort {

    Optional<Order> findOrderById(String orderId);
}
