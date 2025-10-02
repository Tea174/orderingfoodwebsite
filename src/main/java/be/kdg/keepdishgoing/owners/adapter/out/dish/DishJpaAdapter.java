package be.kdg.keepdishgoing.owners.adapter.out.dish;

import be.kdg.keepdishgoing.owners.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.owners.domain.Dish;
import be.kdg.keepdishgoing.owners.domain.DishId;
import be.kdg.keepdishgoing.owners.domain.RestaurantId;
import be.kdg.keepdishgoing.owners.port.out.dish.DeleteDishPort;
import be.kdg.keepdishgoing.owners.port.out.dish.LoadDishesPort;
import be.kdg.keepdishgoing.owners.port.out.dish.SaveDishPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DishJpaAdapter implements LoadDishesPort, SaveDishPort, DeleteDishPort {

    private final DishJpaRepository dishJpaRepository;
    private final Mapper mapper;

    public DishJpaAdapter(DishJpaRepository dishJpaRepository, Mapper mapper) {
        this.dishJpaRepository = dishJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Dish> loadByDishId(DishId dishId) {
        return dishJpaRepository.findByDishId(dishId.id())
                .map(mapper::toDomainDish);
    }

    @Override
    public List<Dish> loadByRestaurantId(RestaurantId restaurantId) {
        return dishJpaRepository.findByRestaurant_RestaurantId(restaurantId.id())
                .stream()
                .map(mapper::toDomainDish)
                .collect(Collectors.toList());
    }

    @Override
    public Dish save(Dish dish) {
        // Load the parent restaurant entity
        var restaurantEntity = // need to inject RestaurantJpaRepository
                DishJpaEntity entity = mapper.toEntityDish(dish, restaurantEntity);
        DishJpaEntity savedEntity = dishJpaRepository.save(entity);
        return mapper.toDomainDish(savedEntity);
    }

    @Override
    public void delete(DishId dishId) {
        dishJpaRepository.deleteByDishId(dishId.id());
    }
}