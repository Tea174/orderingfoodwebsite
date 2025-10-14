package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;

import be.kdg.keepdishgoing.restaurants.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishgoing.restaurants.adapter.out.owner.OwnerJpaEntity;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name="restaurants", schema = "kdg_restaurants")
public class RestaurantJpaEntity {

    @Id
    @Column(name = "restaurant_id")
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private OwnerJpaEntity ownerId;

    @Column(nullable = false)
    private String  name;
    @Column(nullable = false)
    private String  address;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String pictureURL;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfCuisine cuisine;

    @Column(nullable = false)
    private Time preparationTime;
    @Column(nullable = false)
    private Time openingTime;


    @Column
    private Timestamp created_at;
    @Column
    private Timestamp updated_at;

    @Column(name = "min_price")
    private Double minPrice;

    @Column(name = "max_price")
    private Double maxPrice;
    @Column(name = "estimated_delivery_time")
    private Time estimatedDeliveryTime; // in minutes


    @Column
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<DishJpaEntity> dishes;


    public RestaurantJpaEntity() {
        this.uuid = UUID.randomUUID();
    }
    public void setOwner(OwnerJpaEntity owner) {
        this.ownerId = owner;
    }

    public UUID getOwnerId() {
        return ownerId != null ? ownerId.getUuid() : null;
    }

    public RestaurantJpaEntity(UUID uuid, OwnerJpaEntity ownerId, String name, String address, String email, String pictureURL, TypeOfCuisine cuisine, Time preparationTime, Time openingTime, List<DishJpaEntity> dishes) {
        this.uuid = uuid;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.pictureURL = pictureURL;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.openingTime = openingTime;
        this.dishes = dishes;
    }
}
