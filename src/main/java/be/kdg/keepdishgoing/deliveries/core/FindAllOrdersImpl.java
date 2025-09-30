package be.kdg.keepdishgoing.deliveries.core;

import be.kdg.keepdishgoing.deliveries.domain.Order;
import be.kdg.keepdishgoing.deliveries.port.in.FindAllOrdersPort;
import be.kdg.keepdishgoing.deliveries.port.out.LoadOrdersPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllOrdersImpl implements FindAllOrdersPort {
    private final LoadOrdersPort loadOrderPort;


    public FindAllOrdersImpl(LoadOrdersPort loadOrderPort) {
        this.loadOrderPort = loadOrderPort;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}
