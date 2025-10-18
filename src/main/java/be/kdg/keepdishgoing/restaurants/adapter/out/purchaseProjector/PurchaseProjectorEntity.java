package be.kdg.keepdishgoing.restaurants.adapter.out.purchaseProjector;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "purchase_projector", schema = "kdg_restaurants")
@Getter
@Setter
public class PurchaseProjectorEntity {
    @Id
    private UUID id;
    private UUID purchaseId;
    private UUID restaurantId;

}
