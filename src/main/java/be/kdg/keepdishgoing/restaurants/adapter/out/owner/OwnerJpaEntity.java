package be.kdg.keepdishgoing.restaurants.adapter.out.owner;

import be.kdg.keepdishgoing.restaurants.adapter.out.restaurant.RestaurantJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "owners", schema = "kdg_restaurants")
public class OwnerJpaEntity {

    @Id
    @Column(name = "owner_id")
    private UUID uuid;
    @Column(name = "keycloak_subject_id", unique = true)
    private String keycloakSubjectId;
    @OneToOne(mappedBy = "ownerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private RestaurantJpaEntity restaurant;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Integer phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column
    private Timestamp created_at;
    @Column
    private Timestamp updated_at;

    public OwnerJpaEntity() {
        this.uuid = UUID.randomUUID();
    }

    public OwnerJpaEntity(UUID uuid, RestaurantJpaEntity restaurant, String firstName, String lastName, String email, Integer phoneNumber, String address) {
        this.uuid = uuid;
        this.restaurant = restaurant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}