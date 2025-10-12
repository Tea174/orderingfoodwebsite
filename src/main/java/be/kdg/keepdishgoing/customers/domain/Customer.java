package be.kdg.keepdishgoing.customers.domain;

import be.kdg.keepdishgoing.common.events.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
