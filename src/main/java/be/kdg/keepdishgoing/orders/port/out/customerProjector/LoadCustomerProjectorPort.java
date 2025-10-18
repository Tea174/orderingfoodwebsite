package be.kdg.keepdishgoing.orders.port.out.customerProjector;

import be.kdg.keepdishgoing.orders.domain.customerRecord.CustomerProjectorRecord;
import java.util.Optional;
import java.util.UUID;

public interface LoadCustomerProjectorPort {
    Optional<CustomerProjectorRecord> findById(UUID customerId);
}