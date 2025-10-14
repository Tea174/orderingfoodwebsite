package be.kdg.keepdishgoing.orders.adapter.out.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customers_projector", schema = "kdg_orders")
@Getter
@Setter
public class CustomerProjectorEntity {
    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;
}