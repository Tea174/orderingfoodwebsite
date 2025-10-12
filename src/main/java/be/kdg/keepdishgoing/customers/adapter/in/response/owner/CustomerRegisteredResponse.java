package be.kdg.keepdishgoing.customers.adapter.in.response.owner;

import be.kdg.keepdishgoing.customers.domain.Customer;

import java.util.UUID;

public record CustomerRegisteredResponse (
        UUID customerId,
        String email,
        String fullName,
        String message
){
    public static CustomerRegisteredResponse fromDomain(Customer customer) {
        return new CustomerRegisteredResponse(
                customer.getCustomerId().id(),
                customer.getEmail(),
                customer.getFullName(),
                "Registration successful"
        );
    }

}
