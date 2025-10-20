package be.kdg.keepdishgoing.orders.adapter.out.customerProjector;

import be.kdg.keepdishgoing.orders.domain.customerRecord.CustomerProjectorRecord;
import be.kdg.keepdishgoing.orders.port.out.customerProjector.LoadCustomerProjectorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerProjectorJpaAdapter implements LoadCustomerProjectorPort {
    private final CustomerProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(CustomerProjectorJpaAdapter.class);

    public CustomerProjectorJpaAdapter(CustomerProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(CustomerProjectorRecord customerProjectorRecord) {
        logger.debug("Saving CustomerProjectorRecord");
        CustomerProjectorEntity entity = new CustomerProjectorEntity();
        entity.setCustomerId(customerProjectorRecord.customerId());
        entity.setKeycloakId(customerProjectorRecord.keycloakId());
        entity.setFirstName(customerProjectorRecord.firstName());
        entity.setLastName(customerProjectorRecord.lastName());
        entity.setEmail(customerProjectorRecord.email());
        entity.setPhoneNumber(customerProjectorRecord.phoneNumber());
        entity.setAddress(customerProjectorRecord.address());
        logger.debug("Saving " + entity);
        repository.save(entity);
    }

    @Override
    public Optional<CustomerProjectorRecord> findById(UUID customerId) {
        logger.debug("Retrieving CustomerProjectorRecord");
        return repository.findById(customerId)
                .map(entity -> new CustomerProjectorRecord(
                        entity.getCustomerId(),
                        entity.getKeycloakId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getPhoneNumber(),
                        entity.getAddress()
                ));
    }
}