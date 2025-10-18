package be.kdg.keepdishgoing.orders.adapter.out.restaurantProjector;

import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.orders.adapter.out.dishProjector.DishProjectorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants_projector", schema = "kdg_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantProjectorEntity {

    @Id
    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "owner_keycloak_subject_id", nullable = false)
    private String ownerKeycloakSubjectId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeOfCuisine cuisine;

    @Column(name = "estimated_delivery_time")
    private Time estimatedDeliveryTime;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<DishProjectorEntity> dishes;

}