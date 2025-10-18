package be.kdg.keepdishgoing.orders.adapter.out.customerProjector;

import be.kdg.keepdishgoing.orders.domain.customerRecord.CustomerProjectorRecord;
import be.kdg.keepdishgoing.orders.port.out.customerProjector.LoadCustomerProjectorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerProjectorJpaAdapter  implements LoadCustomerProjectorPort {
    private final CustomerProjectorJpaRepository repository;
    private final Logger logger =  LoggerFactory.getLogger(CustomerProjectorJpaAdapter.class);

    public CustomerProjectorJpaAdapter(CustomerProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(CustomerProjectorRecord customerProjectorRecord) {
        logger.debug("Saving CustomerProjectorRecord");
        CustomerProjectorEntity customerProjectorEntity = new CustomerProjectorEntity();
        customerProjectorEntity.setCustomerId(customerProjectorRecord.customerId());
        customerProjectorEntity.setKeycloakId(customerProjectorRecord.keycloakId());
        logger.debug("Saving " + customerProjectorEntity);
        repository.save(customerProjectorEntity);
    }

    @Override
    public Optional<CustomerProjectorRecord> findById(UUID customerId) {
        logger.debug("Retrieving CustomerProjectorRecord");
        Optional<CustomerProjectorEntity> customerProjectorEntity = repository.findById(customerId);
        return customerProjectorEntity;
    }
}
