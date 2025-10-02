package be.kdg.keepdishgoing.owners.adapter.out.restaurant;

import be.kdg.keepdishgoing.owners.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishgoing.owners.domain.Dish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name="restaurants", schema = "kdg_owners")
@AllArgsConstructor
public class RestaurantJpaEntity {

    @Id
    private UUID restaurantId;
    @Column(nullable = false)
    private UUID ownerId;
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
    private String cuisine;

    @Column(nullable = false)
    private Time preparationTime;
    @Column(nullable = false)
    private Time openingTime;

    @Column(nullable = false)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<DishJpaEntity> dishes;

    public RestaurantJpaEntity() {
        this.restaurantId = UUID.randomUUID();
    }

    public RestaurantJpaEntity(UUID ownerId, String name, String address, String email, String pictureURL, String cuisine, Time preparationTime, Time openingTime) {
        this();
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.pictureURL = pictureURL;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.openingTime = openingTime;
    }
}
