package be.kdg.keepdishgoing.customers.port.out;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.domain.CustomerId;

import java.util.Optional;

public interface LoadCustomerPort {
    Optional<Customer> loadById(CustomerId customerId);
    Optional<Customer> findByKeycloakId(String keycloakId);
    Optional<Customer> loadByEmail(String email);
}
