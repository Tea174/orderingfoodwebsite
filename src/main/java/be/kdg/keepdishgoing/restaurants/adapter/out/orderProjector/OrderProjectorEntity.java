package be.kdg.keepdishgoing.restaurants.adapter.out.orderProjector;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "order_projector", schema = "kdg_delivery")
@Getter
@Setter
public class OrderProjectorEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private UUID restaurantId;

}
