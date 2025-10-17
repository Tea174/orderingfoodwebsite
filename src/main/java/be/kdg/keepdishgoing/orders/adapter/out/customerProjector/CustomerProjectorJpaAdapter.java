package be.kdg.keepdishgoing.orders.adapter.out.customerProjector;

import be.kdg.keepdishgoing.orders.domain.customerRecord.CustomerProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerProjectorJpaAdapter {
    private final CustomerProjectorJpaRepository repository;
    private final Logger logger =  LoggerFactory.getLogger(CustomerProjectorJpaAdapter.class);

    public CustomerProjectorJpaAdapter(CustomerProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(CustomerProjectorRecord customerProjectorRecord) {
        logger.debug("Saving CustomerProjectorRecord");
        CustomerProjectorEntity customerProjectorEntity = new CustomerProjectorEntity();
        customerProjectorEntity.setCustomerId(customerProjectorRecord.customerId());
        customerProjectorEntity.setKeycloakId(customerProjectorRecord.keycloak());
        logger.debug("Saving " + customerProjectorEntity);
        repository.save(customerProjectorEntity);
    }
}
