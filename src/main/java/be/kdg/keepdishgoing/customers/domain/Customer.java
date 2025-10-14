package be.kdg.keepdishgoing.customers.domain;

import be.kdg.keepdishgoing.common.event.DomainEvent;
import be.kdg.keepdishgoing.common.event.customerEvents.CustomerCreatedEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Customer {
    private CustomerId customerId;
    private String keycloakSubjectId;
    private  String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private String Address;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public static Customer createCustomer(String keycloakSubjectId, String firstName,
                                          String lastName, String email,
                                          int phoneNumber, String address){
        Customer customer = new Customer();
        customer.setCustomerId(CustomerId.create());
        customer.setKeycloakSubjectId(keycloakSubjectId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);

        customer.domainEvents.add(new CustomerCreatedEvent(
                LocalDateTime.now(),
                customer.customerId.id(),
                customer.keycloakSubjectId
        ));

        return customer;
    }


    public Customer(CustomerId customerId, String keycloakSubjectId, String firstName, String lastName, String email, int phoneNumber, String address) {
        this.customerId = customerId;
        this.keycloakSubjectId = keycloakSubjectId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        Address = address;
    }

    public Customer(CustomerId customerId, String firstName, String lastName, String email, int phoneNumber, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        Address = address;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Customer() {
    }
    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
