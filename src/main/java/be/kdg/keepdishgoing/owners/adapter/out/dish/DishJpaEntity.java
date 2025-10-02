package be.kdg.keepdishgoing.owners.adapter.out.dish;


import be.kdg.keepdishgoing.owners.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.keepdishgoing.owners.domain.DishId;
import be.kdg.keepdishgoing.owners.domain.DishType;
import be.kdg.keepdishgoing.owners.domain.FoodTag;
import be.kdg.keepdishgoing.owners.domain.RestaurantId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dishes", schema = "kdg_owners")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private UUID dishId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;

    @Column(nullable = false)
    private String dishName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DishType dishType;


    @Column(nullable = false)
    private String foodTags;
    private String description;
    private double price;
    private String pictureURL;

    public UUID getRestaurantId() {
        return restaurant != null ? restaurant.getRestaurantId() : null;
    }


}
