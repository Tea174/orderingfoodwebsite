package be.kdg.keepdishgoing.customers.port.out.customer;

import be.kdg.keepdishgoing.customers.domain.Customer;

public interface CreateCustomerPort {
    Customer save(Customer customer);
}
