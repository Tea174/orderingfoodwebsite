package be.kdg.keepdishgoing.owners.adapter.out.mapper;

import be.kdg.keepdishgoing.owners.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishgoing.owners.adapter.out.owner.OwnerJpaEntity;
import be.kdg.keepdishgoing.owners.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.keepdishgoing.owners.domain.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public Restaurant toDomainRestaurant(RestaurantJpaEntity entity) {
        return new Restaurant(
                RestaurantId.of(entity.getRestaurantId()),
                OwnerId.of(entity.getOwnerId()),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPictureURL(),
                new TypeOfCuisine(entity.getCuisine()), // String → value object
                entity.getPreparationTime(),
                entity.getOpeningTime(),
                entity.getDishes()
                        .stream()
                        .map(this::toDomainDish)
                        .toList()
        );
    }

    public RestaurantJpaEntity toEntityRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setRestaurantId(restaurant.getRestaurantId().id());
        entity.setOwnerId(restaurant.getOwnerId().id());
        entity.setName(restaurant.getName());
        entity.setAddress(restaurant.getAddress());
        entity.setEmail(restaurant.getEmail());
        entity.setPictureURL(restaurant.getPictureURL());
        entity.setCuisine(restaurant.getCuisine().value()); // VO → String
        entity.setPreparationTime(restaurant.getPreparationTime());
        entity.setOpeningTime(restaurant.getOpeningTime());

        // Map dishes with proper parent reference
        List<DishJpaEntity> dishEntities = restaurant.getDishes()
                .stream()
                .map(dish -> toEntityDish(dish, entity))
                .toList();
        entity.setDishes(dishEntities);

        return entity;
    }

    public Dish toDomainDish(DishJpaEntity entity) {
        return new Dish(
                DishId.of(entity.getDishId()),
                RestaurantId.of(entity.getRestaurant().getRestaurantId()), // Get UUID from parent entity
                entity.getDishName(),
                entity.getDishType(), // already enum
                parseFoodTags(entity.getFoodTags()), // String -> List<FoodTag>
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureURL()
        );
    }

    public DishJpaEntity toEntityDish(Dish dish, RestaurantJpaEntity parent) {
        DishJpaEntity entity = new DishJpaEntity();
        entity.setDishId(dish.getDishId().id());
        entity.setRestaurant(parent); // FIXED: use setRestaurant, not setRestaurantId
        entity.setDishName(dish.getName());
        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPictureURL(dish.getPictureURL());
        entity.setDishType(dish.getDishType());
        entity.setFoodTags(joinFoodTags(dish.getFoodTags())); // List<FoodTag> -> String
        return entity;
    }

    private List<FoodTag> parseFoodTags(String tags) {
        if (tags == null || tags.isBlank()) return List.of();
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .map(FoodTag::new) // record FoodTag(String value)
                .toList();
    }

    private String joinFoodTags(List<FoodTag> tags) {
        if (tags == null || tags.isEmpty()) return "";
        return tags.stream()
                .map(FoodTag::value) // record getter
                .collect(Collectors.joining(","));
    }


    public Owner toDomainOwner(OwnerJpaEntity entity) {
        return new Owner(
                OwnerId.of(entity.getOwnerId()),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhoneNumber(),
                entity.getAddress()
        );
    }

    public OwnerJpaEntity toEntityOwner(Owner owner) {
        OwnerJpaEntity entity = new OwnerJpaEntity();
        entity.setOwnerId(owner.getOwnerId().id());
        entity.setFirstName(owner.getFirstName());
        entity.setLastName(owner.getLastName());
        entity.setEmail(owner.getEmail());
        entity.setPassword(owner.getPassword());
        entity.setPhoneNumber(owner.getPhoneNumber());
        entity.setAddress(owner.getAddress());
        return entity;
    }
}