package be.kdg.keepdishgoing.restaurants.domain.restaurant;


import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.common.event.DomainEvent;
import be.kdg.keepdishgoing.common.event.restaurantEvents.RestaurantCreatedEvent;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import lombok.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Restaurant {
    private RestaurantId restaurantId;
    private OwnerId ownerId;
    private String  name;
    private String  address;
    private String email;
    private String pictureURL;
    private TypeOfCuisine cuisine;
    private Time preparationTime;
    private Time openingTime;
    private Double minPrice;
    private Double maxPrice;
    private Time estimatedDeliveryTime;
    private List<Dish> dishes;

    private List<DomainEvent>  domainEvents = new ArrayList<>();

    public void clearDomainEvents() {
        domainEvents.clear();
    }


    public static Restaurant createRestaurant(
            OwnerId ownerId,
            String ownerKeycloakId,
            String name,
            String address,
            String email,
            String pictureURL,
            TypeOfCuisine cuisine,
            Time preparationTime,
            Time openingTime,
            Double minPrice,
            Double maxPrice,
            Time estimatedDeliveryTime,
            List<Dish> dishes
    ) {
        Restaurant restaurant = new Restaurant(
                RestaurantId.create(),
                ownerId,
                name,
                address,
                email,
                pictureURL,
                cuisine,
                preparationTime,
                openingTime,
                minPrice,
                maxPrice,
                estimatedDeliveryTime,
                dishes
        );

        restaurant.domainEvents.add(new RestaurantCreatedEvent(
                LocalDateTime.now(),
                restaurant.restaurantId.id(),
                ownerKeycloakId,
                name,
                address,
                email,
                pictureURL,
                cuisine,
                preparationTime,
                openingTime,
                minPrice,
                maxPrice,
                estimatedDeliveryTime
        ));

        return restaurant;
    }
    public Restaurant(RestaurantId restaurantId, OwnerId ownerId, String name, String address, String email, String pictureURL, TypeOfCuisine cuisine, Time preparationTime, Time openingTime, Double minPrice, Double maxPrice,Time estimatedDeliveryTime, List<Dish> dishes) {
        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.pictureURL = pictureURL;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
        this.openingTime = openingTime;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.dishes = dishes;
    }


}
