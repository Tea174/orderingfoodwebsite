package be.kdg.keepdishgoing.customers.adapter.out;


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
    private Integer phoneNumber;
    @Column(nullable = false)
    private String address;

    public CustomerJpaEntity() {
        this.uuid = UUID.randomUUID();
    }

    public CustomerJpaEntity(String address, Integer phoneNumber, String email, String lastName, String firstName, String keycloakSubjectId, UUID uuid) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.keycloakSubjectId = keycloakSubjectId;
        this.uuid = uuid;
    }
}
