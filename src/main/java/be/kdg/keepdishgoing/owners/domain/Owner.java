package be.kdg.keepdishgoing.owners.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
public class Owner {
    private OwnerId ownerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private String address;

    // Factory method for creating new owners
    public static Owner createOwner(String firstName, String lastName, String email,
                                    String hashedPassword, int phoneNumber, String address) {
        Owner owner = new Owner();
        owner.setOwnerId(OwnerId.create());
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPassword(hashedPassword);
        owner.setPhoneNumber(phoneNumber);
        owner.setAddress(address);
        return owner;
    }

    // Business logic methods
    public void updateProfile(String firstName, String lastName, int phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changePassword(String newHashedPassword) {
        this.password = newHashedPassword;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Owner(OwnerId ownerId, String firstName, String lastName, String email,
                 String password, int phoneNumber, String address) {
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Owner() {
    }
}