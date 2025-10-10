package be.kdg.keepdishgoing.restaurants.adapter.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishState;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;
import be.kdg.keepdishgoing.restaurants.adapter.out.restaurant.RestaurantJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dishes", schema = "kdg_restaurants")
@Getter
@Setter
public class DishJpaEntity {

    @Id
    @Column(name = "dish_id")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Enumerated(EnumType.STRING)
    @Column(name = "dish_type", nullable = false)
    private DishType dishType;

    // FIX: Use @ElementCollection for List of enums
    @ElementCollection(targetClass = FoodTag.class)
    @CollectionTable(
            name = "dish_food_tags",
            schema = "kdg_restaurants",
            joinColumns = @JoinColumn(name = "dish_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "food_tag")
    private List<FoodTag> foodTags = new ArrayList<>();

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(name = "picture_url")
    private String pictureURL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DishState state = DishState.DRAFT; // Default value

    // For draft/publish workflow
    @Column(name = "published_dish_uuid")
    private UUID publishedDishUuid;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
        updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.from(Instant.now());
    }
}