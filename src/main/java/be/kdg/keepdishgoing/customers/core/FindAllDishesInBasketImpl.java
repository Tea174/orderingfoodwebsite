package be.kdg.keepdishgoing.customers.core;

import be.kdg.keepdishgoing.customers.domain.Order;
import be.kdg.keepdishgoing.customers.port.in.FindAllDishesInBasketPort;
import be.kdg.keepdishgoing.customers.port.out.LoadOrderPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllDishesInBasketImpl implements FindAllDishesInBasketPort {
    private final LoadOrderPort loadOrderPort;


    public FindAllDishesInBasketImpl(LoadOrderPort loadOrderPort) {
        this.loadOrderPort = loadOrderPort;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}
