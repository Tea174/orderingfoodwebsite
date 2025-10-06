package be.kdg.keepdishgoing.owners.adapter.out.mapper;

import be.kdg.keepdishgoing.owners.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishgoing.owners.adapter.out.owner.OwnerJpaEntity;
import be.kdg.keepdishgoing.owners.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.keepdishgoing.owners.domain.owner.Owner;
import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;
import be.kdg.keepdishgoing.owners.domain.dish.Dish;
import be.kdg.keepdishgoing.owners.domain.dish.DishId;
import be.kdg.keepdishgoing.owners.domain.dish.FoodTag;
import be.kdg.keepdishgoing.owners.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public Restaurant toDomainRestaurant(RestaurantJpaEntity entity) {
        return new Restaurant(
                RestaurantId.of(entity.getUuid()),
                OwnerId.of(entity.getOwnerId()),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPictureURL(),
                entity.getCuisine(),
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
        entity.setUuid(restaurant.getRestaurantId().id());
        // Note: owner is set separately in the adapter/repository layer
        entity.setName(restaurant.getName());
        entity.setAddress(restaurant.getAddress());
        entity.setEmail(restaurant.getEmail());
        entity.setPictureURL(restaurant.getPictureURL());
        entity.setCuisine(restaurant.getCuisine());
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
        // When loading from DB, use constructor with existing ID
        return new Dish(
                DishId.of(entity.getUuid()),
                RestaurantId.of(entity.getRestaurantId()),
                entity.getDishName(),
                entity.getDishType(),
                parseFoodTags(entity.getFoodTags()),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureURL()
        );
    }

    public DishJpaEntity toEntityDish(Dish dish, RestaurantJpaEntity parent) {
        DishJpaEntity entity = new DishJpaEntity();
        // Use the domain's dishId - it's already generated
        entity.setUuid(dish.getDishId().id());
        entity.setRestaurant(parent);
        entity.setDishName(dish.getName());
        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPictureURL(dish.getPictureURL());
        entity.setDishType(dish.getDishType());
        entity.setFoodTags(joinFoodTags(dish.getFoodTags()));
        return entity;
    }

    private List<FoodTag> parseFoodTags(String tags) {
        if (tags == null || tags.isBlank()) return List.of();
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .map(FoodTag::new)
                .toList();
    }

    private String joinFoodTags(List<FoodTag> tags) {
        if (tags == null || tags.isEmpty()) return "";
        return tags.stream()
                .map(FoodTag::value)
                .collect(Collectors.joining(","));
    }

    public Owner toDomainOwner(OwnerJpaEntity entity) {
        return new Owner(
                OwnerId.of(entity.getUuid()),
                entity.getRestaurant() != null ?
                        RestaurantId.of(entity.getRestaurant().getUuid()) : null,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getAddress()
        );
    }

    public OwnerJpaEntity toEntityOwner(Owner owner) {
        OwnerJpaEntity entity = new OwnerJpaEntity();
        entity.setUuid(owner.getOwnerId().id());
        entity.setFirstName(owner.getFirstName());
        entity.setLastName(owner.getLastName());
        entity.setEmail(owner.getEmail());
        entity.setPhoneNumber(owner.getPhoneNumber());
        entity.setAddress(owner.getAddress());
        // Note: restaurant relationship should be set in the adapter/repository layer
        return entity;
    }
}