package be.kdg.keepdishgoing.restaurants.adapter.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishState;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishType;
import be.kdg.keepdishgoing.restaurants.domain.dish.FoodTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {
    @Query("SELECT d FROM DishJpaEntity d JOIN d.foodTags ft WHERE ft = :foodTag")
    List<DishJpaEntity> findByFoodTag(@Param("foodTag") FoodTag foodTag);

    Optional<DishJpaEntity> findByUuid(UUID dishId);
    List<DishJpaEntity> findByRestaurant_uuid(UUID restaurantId);
    void deleteByUuid(UUID dishId);

    // Find dishes by restaurant and specific state
    List<DishJpaEntity> findByRestaurant_uuidAndState(UUID restaurantId, DishState state);

    // Find dishes by restaurant and multiple states (for customer menu)
    List<DishJpaEntity> findByRestaurant_uuidAndStateIn(UUID restaurantId, List<DishState> states);

    // Find draft for a specific published dish
    Optional<DishJpaEntity> findByPublishedDishUuid(UUID publishedDishId);

    // Find all drafts that are linked to published dishes (pending changes)
    List<DishJpaEntity> findByRestaurant_uuidAndStateAndPublishedDishUuidIsNotNull(
            UUID restaurantId,
            DishState state
    );

    boolean existsByPublishedDishUuid(UUID publishedDishId);

    long countByRestaurant_uuidAndStateAndPublishedDishUuidIsNotNull(
            UUID restaurantId,
            DishState state
    );

    // Find all dishes excluding drafts (for simpler owner view)
    List<DishJpaEntity> findByRestaurant_uuidAndStateNot(UUID restaurantId, DishState state);

    void deleteByPublishedDishUuid(UUID publishedDishId);

    List<DishJpaEntity> findByDishType(DishType dishType);


}