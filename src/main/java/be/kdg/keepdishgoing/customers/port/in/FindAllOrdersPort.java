package be.kdg.keepdishgoing.customers.port.in;



import be.kdg.keepdishgoing.customers.domain.Order;

import java.util.List;

public interface FindAllOrdersPort {
    List<Order> findAll();
}
