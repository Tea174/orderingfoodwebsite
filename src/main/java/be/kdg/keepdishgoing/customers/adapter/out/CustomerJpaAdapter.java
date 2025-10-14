package be.kdg.keepdishgoing.customers.adapter.out;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.domain.CustomerId;
import be.kdg.keepdishgoing.customers.port.out.CreateCustomerPort;
import be.kdg.keepdishgoing.customers.port.out.LoadCustomerPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerJpaAdapter implements LoadCustomerPort, CreateCustomerPort {

    private final CustomerJpaRepository customerJpaRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final Logger logger = LoggerFactory.getLogger(CustomerJpaAdapter.class);

    @Override
    public Customer save(Customer customer) {
        logger.debug("Saving customer {}", customer);
        // domain -> entity
        var entity = new CustomerJpaEntity(
                customer.getCustomerId().id(),
                customer.getKeycloakSubjectId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress()
        );
        var savedEntity = customerJpaRepository.save(entity);
        Customer savedDomain = new Customer();
        savedDomain.setCustomerId( CustomerId.of(savedEntity.getUuid()));
        savedDomain.setKeycloakSubjectId(savedEntity.getKeycloakSubjectId());
        savedDomain.setFirstName(savedEntity.getFirstName());
        savedDomain.setLastName(savedEntity.getLastName());
        savedDomain.setEmail(savedEntity.getEmail());
        savedDomain.setPhoneNumber(savedEntity.getPhoneNumber());
        savedDomain.setAddress(savedEntity.getAddress());

        // Publish events
        customer.getDomainEvents().forEach(event -> {
            logger.info("Publishing event: {}", event);
            eventPublisher.publishEvent(event);
        });
        customer.clearDomainEvents();
        return savedDomain;
    }

    @Override
    public Optional<Customer> loadById(CustomerId customerId) {
        logger.debug("Loading Customer by ID {}", customerId);
        return this.customerJpaRepository.findById(customerId.id())
                .map(entity -> {
                    Customer customer = new Customer(
                            CustomerId.of(entity.getUuid()),
                            entity.getKeycloakSubjectId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getPhoneNumber(),
                            entity.getAddress()
                    );
                    return customer;
                });
    }

    @Override
    public Optional<Customer> findByKeycloakId(String keycloakId) {
        return customerJpaRepository.findByKeycloakSubjectId(keycloakId)
                .map(entity -> {
                    Customer customer = new Customer(
                            CustomerId.of(entity.getUuid()),
                            entity.getKeycloakSubjectId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getPhoneNumber(),
                            entity.getAddress()
                    );
                    return customer;
                });
    }

    @Override
    public Optional<Customer> loadByEmail(String email) {
        logger.debug("Loading Customer by Email {}", email);
        return this.customerJpaRepository.findByEmail(email)
                .map(entity -> {
                    Customer customer = new Customer(
                            CustomerId.of(entity.getUuid()),
                            entity.getKeycloakSubjectId(),
                            entity.getFirstName(),
                            entity.getLastName(),
                            entity.getEmail(),
                            entity.getPhoneNumber(),
                            entity.getAddress()
                    );
                    return customer;
                });
    }


}
