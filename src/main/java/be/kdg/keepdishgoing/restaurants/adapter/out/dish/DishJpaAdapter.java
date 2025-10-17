package be.kdg.keepdishgoing.restaurants.adapter.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishState;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.restaurants.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.restaurants.adapter.out.restaurant.RestaurantJpaRepository;
import be.kdg.keepdishgoing.restaurants.domain.dish.*;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.port.out.dish.DeleteDishPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.LoadDishesPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.SaveDishPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DishJpaAdapter implements LoadDishesPort, SaveDishPort, DeleteDishPort{

    private final DishJpaRepository dishJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final Mapper mapper;

    public DishJpaAdapter(DishJpaRepository dishJpaRepository,
                          RestaurantJpaRepository restaurantJpaRepository,
                          Mapper mapper) {
        this.dishJpaRepository = dishJpaRepository;
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Dish> loadByDishId(DishId dishId) {
        return dishJpaRepository.findByUuid(dishId.id())
                .map(mapper::toDomainDish);
    }

    @Override
    public List<Dish> loadByRestaurantId(RestaurantId restaurantId) {
        return dishJpaRepository.findByRestaurant_uuid(restaurantId.id())
                .stream()
                .map(mapper::toDomainDish)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> loadByType(DishType dishType) {
        return dishJpaRepository.findByDishType(dishType)
                .stream()
                .map(mapper::toDomainDish)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> loadByFoodTag(FoodTag foodTag) {
        return dishJpaRepository.findByFoodTag(foodTag)
                .stream()
                .map(mapper::toDomainDish)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> loadPublishedByRestaurantId(RestaurantId restaurantId) {
        return dishJpaRepository.findByRestaurant_UuidAndState(
                        restaurantId.id(),
                        DishState.PUBLISHED
                )
                .stream()
                .map(mapper::toDomainDish)
                .toList();
    }

    @Override
    public Optional<Dish> loadByPublished(DishId dishId) {
        return dishJpaRepository.findPublishedDish(dishId.id())
                .map(mapper::toDomainDish);
    }

    @Override
    public Dish save(Dish dish) {
        var restaurantEntity = restaurantJpaRepository.findById(dish.getRestaurantId().id())
                .orElseThrow(() -> new IllegalArgumentException(
                        "RestaurantProjectorRecord not found with id: " + dish.getRestaurantId().id()
                ));
        DishJpaEntity entity = mapper.toEntityDish(dish, restaurantEntity);
        dishJpaRepository.save(entity);

        return dish;
    }


    @Override
    public void delete(DishId dishId) {

    }
}