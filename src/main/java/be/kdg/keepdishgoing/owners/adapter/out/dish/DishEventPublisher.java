package be.kdg.keepdishgoing.owners.adapter.out.dish;

import be.kdg.keepdishgoing.owners.adapter.out.mapper.Mapper;
import be.kdg.keepdishgoing.owners.adapter.out.restaurant.RestaurantEventPublisher;
import be.kdg.keepdishgoing.owners.adapter.out.restaurant.RestaurantJpaRepository;
import be.kdg.keepdishgoing.owners.domain.Dish;
import be.kdg.keepdishgoing.owners.domain.DishId;
import be.kdg.keepdishgoing.owners.port.out.dish.DeleteDishPort;
import be.kdg.keepdishgoing.owners.port.out.dish.PublicDishesPort;
import be.kdg.keepdishgoing.owners.port.out.dish.SaveDishPort;
import be.kdg.keepdishgoing.owners.port.out.dish.UpdateDishPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DishEventPublisher implements UpdateDishPort, DeleteDishPort, SaveDishPort, PublicDishesPort {

    private final DishJpaRepository dishJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final Mapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public DishEventPublisher(DishJpaRepository dishJpaRepository,
                              RestaurantJpaRepository restaurantJpaRepository,
                              Mapper mapper,
                              ApplicationEventPublisher eventPublisher) {
        this.dishJpaRepository = dishJpaRepository;
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Dish update(Dish dish) {
        // Save to database
        var restaurantEntity = restaurantJpaRepository.findById(dish.getRestaurantId().id())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        var dishEntity = mapper.toEntityDish(dish, restaurantEntity);
        var savedEntity = dishJpaRepository.save(dishEntity);

        // Publish all domain events that occurred
        dish.getDishEvents().forEach(eventPublisher::publishEvent);

//        // Clear events after publishing
//        dish.clearDomainEvents();

        return mapper.toDomainDish(savedEntity);
    }

    @Override
    public void delete(DishId dishId) {

    }

    @Override
    public Dish save(Dish dish) {
        return null;
    }
}