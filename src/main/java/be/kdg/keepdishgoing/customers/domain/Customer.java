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

    private  String firstName;
    private String lastName;
    private String email;
    private int number;
    private String Address;

    private final List<DomainEvent> domainEvents = new ArrayList<>();


}
