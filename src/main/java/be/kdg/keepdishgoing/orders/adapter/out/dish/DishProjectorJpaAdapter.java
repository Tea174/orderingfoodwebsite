package be.kdg.keepdishgoing.orders.adapter.out.dish;

import be.kdg.keepdishgoing.orders.adapter.out.restaurant.RestaurantProjectorEntity;
import be.kdg.keepdishgoing.orders.adapter.out.restaurant.RestaurantProjectorJpaRepository;
import be.kdg.keepdishgoing.orders.domain.dishRecord.DishProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DishProjectorJpaAdapter {

    private final DishProjectorJpaRepository dishRepository;
    private final RestaurantProjectorJpaRepository restaurantRepository;
    private final Logger logger =  LoggerFactory.getLogger(DishProjectorJpaAdapter.class);

    public DishProjectorJpaAdapter(DishProjectorJpaRepository dishRepository,
                                   RestaurantProjectorJpaRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void save(DishProjectorRecord dishProjectorRecord) {
        logger.debug("Saving DishProjectorRecord");
        RestaurantProjectorEntity restaurant = restaurantRepository
                .findById(dishProjectorRecord.restaurantId())
                .orElseThrow(() -> new IllegalStateException("RestaurantProjectorRecord not found: " + dishProjectorRecord.restaurantId()));

        DishProjectorEntity entity = new DishProjectorEntity();
        entity.setDishId(dishProjectorRecord.dishId());
        entity.setRestaurant(restaurant);
        entity.setDishName(dishProjectorRecord.name());
        entity.setDishType(dishProjectorRecord.dishType());
        logger.debug("setting entity from DishProjector Record");
        entity.setFoodTags(dishProjectorRecord.foodTags());
        entity.setDescription(dishProjectorRecord.description());
        entity.setPrice(dishProjectorRecord.price());
        entity.setPictureURL(dishProjectorRecord.pictureURL());
        entity.setInStock(dishProjectorRecord.inStock());
        logger.debug("Saving DishProjectorEntity");
        dishRepository.save(entity);
    }

    public void updateStock(UUID dishId, boolean inStock) {
        DishProjectorEntity entity = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException("DishProjectorRecord not found: " + dishId));
        entity.setInStock(inStock);
        logger.debug("Update Stock DishProjectorEntity");
        dishRepository.save(entity);
    }

    public DishProjectorEntity  findById(UUID dishId) {
        logger.debug("Finding DishProjectorEntity");
        return dishRepository.findById(dishId).orElseThrow(() -> new IllegalStateException("DishProjectorRecord not found: " + dishId));
    }
}
