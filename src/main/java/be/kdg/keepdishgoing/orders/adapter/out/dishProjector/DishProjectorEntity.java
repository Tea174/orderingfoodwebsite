package be.kdg.keepdishgoing.orders.adapter.out.dishProjector;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.orders.adapter.out.restaurantProjector.RestaurantProjectorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dishes_projector", schema = "kdg_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishProjectorEntity {

    @Id
    @Column(name = "dish_id")
    private UUID dishId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantProjectorEntity restaurant;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Enumerated(EnumType.STRING)
    @Column(name = "dish_type", nullable = false)
    private DishType dishType;

    @ElementCollection(targetClass = FoodTag.class)
    @CollectionTable(
            name = "dish_food_tags",
            schema = "kdg_orders",
            joinColumns = @JoinColumn(name = "dish_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "food_tag")
    private List<FoodTag> foodTags = new ArrayList<>();

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(name = "picture_url")
    private String pictureURL;

    @Column(name = "in_stock", nullable = false)
    private boolean inStock = false;
}