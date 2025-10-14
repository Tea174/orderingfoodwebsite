package be.kdg.keepdishgoing.customers.core;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.domain.CustomerId;
import be.kdg.keepdishgoing.customers.port.in.GetCustomerUseCase;
import be.kdg.keepdishgoing.customers.port.in.RegisterCustomerUseCase;
import be.kdg.keepdishgoing.customers.port.out.CreateCustomerPort;
import be.kdg.keepdishgoing.customers.port.out.LoadCustomerPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service@Transactional@AllArgsConstructor
public class CustomerService implements RegisterCustomerUseCase, GetCustomerUseCase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LoadCustomerPort loadCustomerPort;
    private final CreateCustomerPort createCustomerPort;


    @Override
    public CustomerId register(RegisterCustomerCommand command) {
        logger.info("check if email address exists");
        loadCustomerPort.loadByEmail(command.email()).ifPresent(owner -> {
            throw new IllegalArgumentException("Email already registered");
        });
        Customer customer = Customer.createCustomer(
                command.keycloakSubjectId(),
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phoneNumber(),
                command.address()
        );

        Customer savedCustomer = createCustomerPort.save(customer);
        return savedCustomer.getCustomerId();
    }

    @Override
    public Customer getCustomerById(CustomerId customerId) {
        return loadCustomerPort.loadById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id " + customerId));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return loadCustomerPort.loadByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with email " + email));
    }

    @Override
    public Customer getCustomerByKeycloakId(String keycloakSubjectId) {
        return null;
    }
}
