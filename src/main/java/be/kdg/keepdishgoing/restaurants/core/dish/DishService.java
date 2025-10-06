package be.kdg.keepdishgoing.restaurants.core.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.port.in.dish.*;
import be.kdg.keepdishgoing.restaurants.port.out.dish.DeleteDishPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.LoadDishesPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.SaveDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DishService implements
        AddDishUseCase,
        UpdateDishUseCase,
        DeleteDishUseCase,
        PublishDishUseCase,
        GetDishesByRestaurantUseCase {

    private final SaveDishPort saveDishPort;
    private final LoadDishesPort loadDishesPort;
    private final DeleteDishPort deleteDishPort;

    public DishService(SaveDishPort saveDishPort,
                       LoadDishesPort loadDishesPort,
                       DeleteDishPort deleteDishPort) {
        this.saveDishPort = saveDishPort;
        this.loadDishesPort = loadDishesPort;
        this.deleteDishPort = deleteDishPort;
    }

    @Override
    public DishId addDish(AddDishCommand command) {
        Dish dish = Dish.createDish(
                command.restaurantId(),
                command.name(),
                command.dishType(),
                command.foodTags(),
                command.description(),
                command.price(),
                command.pictureURL()
        );

        Dish savedDish = saveDishPort.save(dish);
        return savedDish.getDishId();
    }

    @Override
    public void updateDish(UpdateDishCommand command) {
        Dish dish = loadDishesPort.loadByDishId(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

        dish.updateDetails(
                command.name(),
                command.dishType(),
                command.foodTags(),
                command.description(),
                command.price(),
                command.pictureURL()
        );

        saveDishPort.save(dish);
    }

    @Override
    public void deleteDish(DishId dishId) {
        deleteDishPort.delete(dishId);
    }

    @Override
    public void publishDish(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

        dish.publish();
        saveDishPort.save(dish);
    }

    @Override
    public void unpublishDish(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

        dish.unpublish();
        saveDishPort.save(dish);
    }

    @Override
    public void markOutOfStock(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

        dish.markOutOfStock();
        saveDishPort.save(dish);
    }

    @Override
//    @Transactional(readOnly = true)
    public List<Dish> getDishesByRestaurant(RestaurantId restaurantId) {
        return loadDishesPort.loadByRestaurantId(restaurantId);
    }
}