package be.kdg.keepdishgoing.orders.port.out.order;


import be.kdg.keepdishgoing.orders.domain.order.Order;

public interface SaveOrderPort {
    void saveOrder(Order order);
}
