package be.kdg.keepdishgoing.customers.adapter.out;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.domain.CustomerId;
import be.kdg.keepdishgoing.customers.port.out.customer.CreateCustomerPort;
import be.kdg.keepdishgoing.customers.port.out.customer.LoadCustomerPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerJpaAdapter implements LoadCustomerPort, CreateCustomerPort {

    private final CustomerJpaRepository customerJpaRepository;
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
        // entity to domain
        return new Customer(
                CustomerId.of(savedEntity.getUuid()),
                savedEntity.getFirstName(),
                customer.getLastName(),
                savedEntity.getEmail(),
                savedEntity.getPhoneNumber(),
                savedEntity.getAddress()
        );
    }

    @Override
    public Optional<Customer> loadById(CustomerId customerId) {
        logger.debug("Loading Customer by ID {}", customerId);
        return this.customerJpaRepository.findById(customerId.id())
                .map(entity -> new Customer(CustomerId.of(entity.getUuid()),entity.getFirstName(), entity.getLastName(),entity.getEmail(),entity.getPhoneNumber(), entity.getAddress()));
    }

    @Override
    public Optional<Customer> findByKeycloakId(String keycloakId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> loadByEmail(String email) {
        logger.debug("Loading Customer by Email {}", email);
        return this.customerJpaRepository.findByEmail(email)
                .map(entity -> new Customer(CustomerId.of(entity.getUuid()),entity.getFirstName(), entity.getLastName(),entity.getEmail(),entity.getPhoneNumber(), entity.getAddress()));
    }


}
