package be.kdg.keepdishgoing.restaurants.core.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.port.in.dish.*;
import be.kdg.keepdishgoing.restaurants.port.out.dish.DeleteDishPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.LoadDishesPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.PublishDishEventsPort;
import be.kdg.keepdishgoing.restaurants.port.out.dish.SaveDishPort;
import be.kdg.keepdishgoing.restaurants.port.out.restaurant.PublishRestaurantEventsPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DishService implements
        AddDishUseCase,
        UpdateDishUseCase,
        DeleteDishUseCase,
        PublishDishUseCase,
        GetDishesByRestaurantUseCase,
        GetDishUseCase,
        FilterDishesUseCase{

    private final SaveDishPort saveDishPort;
    private final LoadDishesPort loadDishesPort;
    private final DeleteDishPort deleteDishPort;
    private final PublishDishEventsPort publishDishEventsPort;

    @Override
    public List<PublishedDishDto> getPublishedDishesByRestaurant(RestaurantId restaurantId) {
        return loadDishesPort.loadPublishedByRestaurantId(restaurantId)
                .stream()
                .map(dish -> new PublishedDishDto(
                        dish.getDishId(),
                        dish.getName(),
                        dish.getPrice(),
                        dish.isInStock(),
                        dish.getRestaurantId()
                ))
                .toList();
    }

    @Override
    public PublishedDishDto getPublishedDish(DishId dishId) {
        Dish dish = loadDishesPort.loadByPublished(dishId)
                .orElseThrow(() -> new IllegalArgumentException("DishProjectorRecord not found"));

        if (!dish.isPublished()) {
            throw new IllegalStateException("DishProjectorRecord is not published");
        }

        return new PublishedDishDto(
                dish.getDishId(),
                dish.getName(),
                dish.getPrice(),
                dish.isInStock(),
                dish.getRestaurantId()
        );
    }


    @Override
    public Dish getDishById(DishId dishId) {
        return loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "DishProjectorRecord not found with id: " + dishId.id()));
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
        publishDishEventsPort.publishEvents(savedDish);
        return savedDish.getDishId();
    }

    @Override
    public void updateDish(UpdateDishCommand command) {
        Dish dish = loadDishesPort.loadByDishId(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("DishProjectorRecord not found"));

        dish.updateDetails(
                command.name(),
                command.dishType(),
                command.foodTags(),
                command.description(),
                command.price(),
                command.pictureURL()
        );

        saveDishPort.save(dish);
        publishDishEventsPort.publishEvents(dish);
    }

    @Override
    public void deleteDish(DishId dishId) {
        deleteDishPort.delete(dishId);
    }

    @Override
    public void publishDish(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("DishProjectorRecord not found"));
        System.out.println("Before publish: " + dish.getState());
        // For NEW dishes (publishedDishUuid is null)
        if(dish.getPublishedDishId() == null) {
            dish.publish();
            System.out.println("After publish: " + dish.getState());
            Dish saved = saveDishPort.save(dish);
            System.out.println("After save: " + saved.getState());
            publishDishEventsPort.publishEvents(saved);
        }
        // For drafts of existing published dishes
        else {
            Dish publishedDish = loadDishesPort.loadByDishId(dish.getPublishedDishId())
                    .orElseThrow(() -> new IllegalArgumentException("Published dish not found"));

            publishedDish.applyDraftChanges(dish);
            saveDishPort.save(publishedDish);
            deleteDishPort.delete(dishId); // Delete the draft
            publishDishEventsPort.publishEvents(publishedDish);
        }
    }

    @Override
    public void unpublishDish(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("DishProjectorRecord not found"));

        dish.unpublish();
        saveDishPort.save(dish);
        publishDishEventsPort.publishEvents(dish);
    }

    @Override
    public void markOutOfStock(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("DishProjectorRecord not found"));

        dish.markOutOfStock();
        saveDishPort.save(dish);
        publishDishEventsPort.publishEvents(dish);
    }

    @Override
    public void markBackInStock(DishId dishId) {
        Dish dish = loadDishesPort.loadByDishId(dishId)
                .orElseThrow(() -> new IllegalArgumentException("DishProjectorRecord not found"));
        dish.markBackInStock();
        saveDishPort.save(dish);
        publishDishEventsPort.publishEvents(dish);
    }

    @Override
//    @Transactional(readOnly = true)
    public List<Dish> getDishesByRestaurant(RestaurantId restaurantId) {
        return loadDishesPort.loadByRestaurantId(restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> filterDishByType(DishType dishType) {
        List<Dish> dishes = loadDishesPort.loadByType(dishType);
        dishes.forEach(dish -> dish.getFoodTags().size());
        return dishes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> filterDishesByFoodTagUseCase(FoodTag foodTag) {
        List<Dish> dishes = loadDishesPort.loadByFoodTag(foodTag);
        dishes.forEach(dish -> dish.getFoodTags().size());
        return dishes;
    }


}