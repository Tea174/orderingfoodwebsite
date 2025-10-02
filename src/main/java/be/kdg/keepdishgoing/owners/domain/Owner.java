package be.kdg.keepdishgoing.owners.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Owner {

    private  String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private String Address;


}
