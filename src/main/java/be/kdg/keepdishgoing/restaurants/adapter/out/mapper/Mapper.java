package be.kdg.keepdishgoing.restaurants.adapter.out.mapper;

import be.kdg.keepdishgoing.restaurants.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishgoing.restaurants.adapter.out.owner.OwnerJpaEntity;
import be.kdg.keepdishgoing.restaurants.adapter.out.owner.OwnerJpaRepository;
import be.kdg.keepdishgoing.restaurants.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.owner.OwnerId;
import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    private final OwnerJpaRepository ownerJpaRepository;

    public Mapper(OwnerJpaRepository ownerJpaRepository) {
        this.ownerJpaRepository = ownerJpaRepository;
    }

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
                entity.getMinPrice(),
                entity.getMaxPrice(),
                entity.getEstimatedDeliveryTime(),
                entity.getDishes() != null ?
                        entity.getDishes().stream()
                                .map(this::toDomainDish)
                                .toList() :
                        List.of()
        );
    }

    public RestaurantJpaEntity toEntityRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity entity = new RestaurantJpaEntity();
        entity.setUuid(restaurant.getRestaurantId().id());

        // Fetch owner entity and set relationship
        OwnerJpaEntity ownerEntity = ownerJpaRepository.findById(restaurant.getOwnerId().id())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        entity.setOwner(ownerEntity);  // Set the relationship

        entity.setName(restaurant.getName());
        entity.setAddress(restaurant.getAddress());
        entity.setEmail(restaurant.getEmail());
        entity.setPictureURL(restaurant.getPictureURL());
        entity.setCuisine(restaurant.getCuisine());
        entity.setPreparationTime(restaurant.getPreparationTime());
        entity.setOpeningTime(restaurant.getOpeningTime());
        entity.setMinPrice(restaurant.getMinPrice());
        entity.setMaxPrice(restaurant.getMaxPrice());
        entity.setEstimatedDeliveryTime(restaurant.getEstimatedDeliveryTime());
        return entity;
    }

    public Dish toDomainDish(DishJpaEntity entity) {
        Dish dish = new Dish(
                DishId.of(entity.getUuid()),
                RestaurantId.of(entity.getRestaurant().getUuid()),
                entity.getDishName(),
                entity.getDishType(),
                entity.getFoodTags(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getPictureURL()
        );

        // Set state and publishedDishUuid after construction
        dish.setState(entity.getState());
        if (entity.getPublishedDishUuid() != null) {
            dish.setPublishedDishId(DishId.of(entity.getPublishedDishUuid()));
        }

        return dish;
    }

    public DishJpaEntity toEntityDish(Dish dish, RestaurantJpaEntity parent) {
        DishJpaEntity entity = new DishJpaEntity();
        entity.setUuid(dish.getDishId().id());
        entity.setRestaurant(parent);
        entity.setPublishedDishUuid(dish.getPublishedDishId() != null ? dish.getPublishedDishId().id() : null);
        entity.setDishName(dish.getName());
        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPictureURL(dish.getPictureURL());
        entity.setDishType(dish.getDishType());
        entity.setFoodTags(dish.getFoodTags());
        entity.setState(dish.getState());

        return entity;
    }


    public Owner toDomainOwner(OwnerJpaEntity entity) {
        return new Owner(
                OwnerId.of(entity.getUuid()),
                entity.getKeycloakSubjectId(),
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
        entity.setKeycloakSubjectId(owner.getKeycloakSubjectId());
        entity.setFirstName(owner.getFirstName());
        entity.setLastName(owner.getLastName());
        entity.setEmail(owner.getEmail());
        entity.setPhoneNumber(owner.getPhoneNumber());
        entity.setAddress(owner.getAddress());
        // Note: restaurant relationship is set in the adapter/repository layer
        return entity;
    }
}