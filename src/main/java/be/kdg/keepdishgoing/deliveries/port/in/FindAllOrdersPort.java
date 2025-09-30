package be.kdg.keepdishgoing.deliveries.port.in;



import be.kdg.keepdishgoing.deliveries.domain.Order;

import java.util.List;

public interface FindAllOrdersPort {
    List<Order> findAll();
}
