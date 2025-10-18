package be.kdg.keepdishgoing.customers.adapter.out.customer;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "customers", schema = "kdg_customers")
public class CustomerJpaEntity {
    @Id
    @Column(name = "customer_id")
    private UUID uuid;
    @Column(name = "keycloak_subject_id", unique = true)
    private String keycloakSubjectId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;

    public CustomerJpaEntity() {
        this.uuid = UUID.randomUUID();
    }

    public CustomerJpaEntity(UUID uuid, String keycloakSubjectId, String firstName, String lastName, String email, String phoneNumber, String address) {
        this.uuid = uuid;
        this.keycloakSubjectId = keycloakSubjectId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
