package be.kdg.keepdishgoing.orders.adapter.in.dishProjector;

import be.kdg.keepdishgoing.common.event.dishEvents.*;
import be.kdg.keepdishgoing.orders.adapter.out.dish.DishProjectorJpaAdapter;
import be.kdg.keepdishgoing.orders.adapter.out.dish.DishProjectorJpaRepository;
import be.kdg.keepdishgoing.orders.domain.dishRecord.DishProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DishProjector {

    private final DishProjectorJpaAdapter dishAdapter;
    private final DishProjectorJpaRepository dishRepository;
    private final Logger logger = LoggerFactory.getLogger(DishProjector.class);

    public DishProjector(DishProjectorJpaAdapter dishAdapter, DishProjectorJpaRepository dishRepository) {
        this.dishAdapter = dishAdapter;
        this.dishRepository = dishRepository;
    }

    @EventListener
    public void on(DishesCreatedEvent event) {
        logger.info("=== RECEIVED DishesCreatedEvent: dishId={}, restaurantId={}",
                event.dishId(), event.restaurantId());
        try {
            DishProjectorRecord dish = new DishProjectorRecord(
                    event.dishId(),
                    event.restaurantId(),
                    event.name(),
                    event.dishType(),
                    event.foodTags(),
                    event.description(),
                    event.price(),
                    event.pictureURL(),
                    event.inStock());
            dishAdapter.save(dish);
            logger.info("=== SAVED dish to Order BC projection");
        } catch (Exception e) {
            logger.error("=== FAILED to save dish", e);
            throw e;
        }
    }

    @EventListener
    public void on(DishPublishedEvent event) {
        logger.info("Received DishPublishedEvent: {}", event.dishId());
        // Check if dish exists first
        dishRepository.findById(event.dishId()).ifPresentOrElse(
                entity -> {
                    entity.setInStock(true);
                    dishRepository.save(entity);
                    logger.info("Dish {} published successfully", event.dishId());
                },
                () -> logger.warn("Dish {} not found in projection, skipping publish event", event.dishId())
        );
    }

    @EventListener
    public void on(DishOutOfStockEvent event) {
        dishRepository.findById(event.dishId()).ifPresent(entity -> {
            entity.setInStock(false);
            dishRepository.save(entity);
        });
    }

    @EventListener
    public void on(DishBackInStockEvent event) {
        dishRepository.findById(event.dishId()).ifPresent(entity -> {
            entity.setInStock(true);
            dishRepository.save(entity);
        });
    }

    @EventListener
    public void on(DishUpdatedEvent event) {
        dishRepository.findById(event.dishId()).ifPresentOrElse(
                entity -> {
                    entity.setDishName(event.name());
                    entity.setDishType(event.dishType());
                    entity.setFoodTags(event.foodTags());
                    entity.setDescription(event.description());
                    entity.setPrice(event.price());
                    entity.setPictureURL(event.pictureURL());
                    dishRepository.save(entity);
                },
                () -> logger.warn("Dish {} not found, cannot update", event.dishId())
        );
    }
}