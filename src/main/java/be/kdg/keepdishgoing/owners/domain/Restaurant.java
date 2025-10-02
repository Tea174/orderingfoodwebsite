package be.kdg.keepdishgoing.owners.domain;


import lombok.*;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class Restaurant {
    private RestaurantId restaurantId;
    private OwnerId  ownerId;
    private String  name;
    private String  address;
    private String email;
    private String pictureURL;
    private TypeOfCuisine cuisine;
    private Time preparationTime;
    private Time openingTime;
    private List<Dish> dishes;

    public static Restaurant createForOwner(
            OwnerId ownerId,
            String name,
            String address,
            String email,
            String pictureURL,
            TypeOfCuisine cuisine,
            Time preparationTime,
            Time openingTime,
            List<Dish> dishes
    ) {
        return new Restaurant(
                RestaurantId.create(),
                ownerId,
                name,
                address,
                email,
                pictureURL,
                cuisine,
                preparationTime,
                openingTime,
                dishes
        );
    }

    public Restaurant(RestaurantId restaurantId, OwnerId ownerId, String name, String address, String email, String pictureURL, TypeOfCuisine cuisine, Time preparationTime, Time openingTime, List<Dish> dishes) {
        this.restaurantId = restaurantId;
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", pictureURL='" + pictureURL + '\'' +
                ", cuisine=" + cuisine +
                ", preparationTime=" + preparationTime +
                ", openingTime=" + openingTime +
                ", dishes=" + dishes +
                '}';
    }
}
