package be.kdg.keepdishgoing.deliveries.adapter.out.requestProjector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "request_projector", schema = "kdg_deliveries")
@Getter
@Setter
public class RequestProjectorEntity {
    @Id
    private UUID requestId;
    private UUID restaurantId;

    // Identity (one will be null)
    private UUID customerId;        // for customers
    private String guestName;       // for guests
    private String guestEmail;      // for guests

    // Contact & Delivery (ALWAYS needed for both customer & guest)
    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String recipientPhone;

    @Column(nullable = false)
    private String deliveryAddress;

}
