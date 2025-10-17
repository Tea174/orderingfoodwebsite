package be.kdg.keepdishgoing.restaurants.port.out.orderProjector;

import be.kdg.keepdishgoing.restaurants.domain.orderRecord.OrderProjectorRecord;

import java.util.Optional;
import java.util.UUID;

public interface LoadOrderProjectorPort {
    Optional<OrderProjectorRecord> findByOrderId(UUID orderId);
}