package be.kdg.keepdishgoing.customers.port.in;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.domain.CustomerId;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;

public interface GetCustomerUseCase {
    Customer getCustomerById(CustomerId customerId);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByKeycloakId(String keycloakSubjectId);
}
