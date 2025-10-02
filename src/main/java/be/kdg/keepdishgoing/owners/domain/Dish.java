package be.kdg.keepdishgoing.owners.domain;


import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private DishId dishId;
    private RestaurantId restaurantId;
    private String name;
    private DishType dishType;
    private List<FoodTag> foodTags;
    private String description;
    private double price;
    private String pictureURL;

    public Dish createDish(RestaurantId restaurantId, String name, DishType dishType, List<FoodTag> foodTags, String description, double price, String pictureURL) {
        Dish dish = new Dish();
            dish.setRestaurantId(restaurantId);
            dish.setName(name);
            dish.setDishType(dishType);
            dish.setFoodTags(foodTags);
            dish.setDescription(description);
            dish.setPrice(price);
            dish.setPictureURL(pictureURL);
        return dish;
    }

}
