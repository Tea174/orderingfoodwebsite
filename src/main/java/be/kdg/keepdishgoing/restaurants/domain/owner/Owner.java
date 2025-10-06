package be.kdg.keepdishgoing.restaurants.domain.owner;

import be.kdg.keepdishgoing.common.events.DomainEvent;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Owner {
    private OwnerId ownerId;
    private RestaurantId restaurantId; // can be null first
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private String address;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // Factory method for creating new owners
    public static Owner createOwner(String firstName, String lastName, String email,
                                int phoneNumber, String address) {
        Owner owner = new Owner();
        owner.setOwnerId(OwnerId.create());
        owner.setRestaurantId(null);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        owner.setAddress(address);
        return owner;
    }

    // Method to link restaurant after creation
    public void assignRestaurant(RestaurantId restaurantId) {
        if (this.restaurantId != null) {
            throw new IllegalStateException("Owner already has a restaurant");
        }
        this.restaurantId = restaurantId;
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


    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Owner(OwnerId ownerId, RestaurantId restaurantId, String firstName, String lastName, String email,  int phoneNumber, String address) {
        this.ownerId = ownerId;
        this.restaurantId = restaurantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Owner() {
    }
}