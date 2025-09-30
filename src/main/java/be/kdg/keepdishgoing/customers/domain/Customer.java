package be.kdg.keepdishgoing.customers.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Customer {

    private  String firstName;
    private String lastName;
    private String email;
    private String password;
    private int number;
    private String Address;


}
