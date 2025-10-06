package be.kdg.keepdishgoing.restaurants.domain.dish;


import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.domain.event.dish.DishEvent;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Dish {
    private DishId dishId;
    private RestaurantId restaurantId;
    private String name;
    private DishType dishType;
    private List<FoodTag> foodTags;
    private String description;
    private double price;
    private String pictureURL;
    private DishState state;

    private final List<DishEvent> dishEvents = new ArrayList<>();

    // Factory method for creating new dishes
    public static Dish createDish(RestaurantId restaurantId, String name, DishType dishType,
                                  List<FoodTag> foodTags, String description, double price, String pictureURL) {
        Dish dish = new Dish();
        dish.setDishId(DishId.create()); // Auto-generate ID
        dish.setRestaurantId(restaurantId);
        dish.setName(name);
        dish.setDishType(dishType);
        dish.setFoodTags(foodTags);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setPictureURL(pictureURL);
        dish.setState(DishState.UNPUBLISHED); // Default state
        return dish;
    }

    // Business logic methods
    public void updateDetails(String name, DishType dishType, List<FoodTag> foodTags,
                              String description, double price, String pictureURL) {
        this.name = name;
        this.dishType = dishType;
        this.foodTags = foodTags;
        this.description = description;
        this.price = price;
        this.pictureURL = pictureURL;
    }

    public void publish() {
        this.state = DishState.PUBLISHED;
    }

    public void unpublish() {
        this.state = DishState.UNPUBLISHED;
    }

    public void markOutOfStock() {
        this.state = DishState.OUTOFSTOCK;
    }

    public Dish(DishId dishId, RestaurantId restaurantId, String name, DishType dishType,
                List<FoodTag> foodTags, String description, double price, String pictureURL) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.dishType = dishType;
        this.foodTags = foodTags;
        this.description = description;
        this.price = price;
        this.pictureURL = pictureURL;
        this.state = DishState.UNPUBLISHED;
    }

    public Dish() {
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId=" + dishId +
                ", restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", dishType=" + dishType +
                ", foodTags=" + foodTags +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", pictureURL='" + pictureURL + '\'' +
                ", state=" + state +
                '}';
    }
}
