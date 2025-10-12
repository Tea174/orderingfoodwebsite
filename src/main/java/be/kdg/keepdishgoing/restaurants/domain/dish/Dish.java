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
    private boolean inStock = false;

    // Links draft to its published version
    private DishId publishedDishId;  // null if this is not a draft

    private final List<DishEvent> dishEvents = new ArrayList<>();

    // Factory method for creating new dishes
    public static Dish createDish(RestaurantId restaurantId, String name, DishType dishType,
                                  List<FoodTag> foodTags, String description, double price, String pictureURL) {
        Dish dish = new Dish();
        dish.setDishId(DishId.create());
        dish.setRestaurantId(restaurantId);
        dish.setName(name);
        dish.setDishType(dishType);
        dish.setFoodTags(foodTags);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setPictureURL(pictureURL);
        dish.setState(DishState.DRAFT);
        return dish;
    }

    // Create a draft copy of an existing dish
    public Dish createDraftCopy() {
        Dish draft = new Dish();
        draft.setDishId(DishId.create()); // New ID for draft
        draft.setRestaurantId(this.restaurantId);
        draft.setName(this.name);
        draft.setDishType(this.dishType);
        draft.setFoodTags(new ArrayList<>(this.foodTags));
        draft.setDescription(this.description);
        draft.setPrice(this.price);
        draft.setPictureURL(this.pictureURL);
        draft.setState(DishState.DRAFT);
        draft.setPublishedDishId(this.dishId); // Link to original
        return draft;
    }

    // Business logic methods
    public void updateDetails(String name, DishType dishType, List<FoodTag> foodTags,
                              String description, double price, String pictureURL) {
        if (this.state == DishState.PUBLISHED) {
            throw new IllegalStateException("Cannot directly edit a published dish. Create a draft first.");
        }
        this.name = name;
        this.dishType = dishType;
        this.foodTags = foodTags;
        this.description = description;
        this.price = price;
        this.pictureURL = pictureURL;
    }

    public void applyDraftChanges(Dish draft) {
        if (!draft.isDraft()) {
            throw new IllegalArgumentException("Can only apply changes from a draft dish");
        }
        if (draft.publishedDishId == null || !draft.publishedDishId.equals(this.dishId)) {
            throw new IllegalArgumentException("Draft is not linked to this dish");
        }

        this.name = draft.name;
        this.dishType = draft.dishType;
        this.foodTags = new ArrayList<>(draft.foodTags);
        this.description = draft.description;
        this.price = draft.price;
        this.pictureURL = draft.pictureURL;
    }

    public void publish() {
        if (this.state == DishState.DRAFT && this.publishedDishId != null) {
            throw new IllegalStateException("Cannot publish a draft directly. Use publishDraft() instead.");
        }
        this.state = DishState.PUBLISHED;
    }

    public void unpublish() {
        this.state = DishState.UNPUBLISHED;
    }
    public void markOutOfStock() {
        if (this.state != DishState.PUBLISHED) {
            throw new IllegalStateException("Only published dishes can be marked out of stock");
        }
        this.inStock = false;
    }

    public void markBackInStock() {
        if (this.state != DishState.PUBLISHED) {
            throw new IllegalStateException("Only published dishes can have stock");
        }
        this.inStock = true;
    }

    public boolean isAvailable() {
        return this.state == DishState.PUBLISHED && this.inStock;
    }
    public boolean isDraft() {
        return this.state == DishState.DRAFT;
    }

    public boolean isPublished() {
        return this.state == DishState.PUBLISHED;
    }

    public List<FoodTag> getFoodTags() {
        return foodTags == null ? List.of() : foodTags;
    }

    public boolean hasDraft() {
        return false;
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
