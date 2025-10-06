package be.kdg.keepdishgoing.owners.adapter.out.dish;


import be.kdg.keepdishgoing.owners.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.keepdishgoing.owners.domain.dish.DishType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "dishes", schema = "kdg_owners")
@AllArgsConstructor
@Getter
@Setter
public class DishJpaEntity {
    @Id
    @Column(name = "dish_id")
    private UUID uuid;
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
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double price;
    @Column
    private String pictureURL;
    @Column
    private Timestamp created_at;
    @Column
    private Timestamp updated_at;

    public DishJpaEntity() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getRestaurantId() {
        return restaurant != null ? restaurant.getUuid() : null;
    }


}
