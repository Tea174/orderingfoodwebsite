package be.kdg.keepdishgoing.restaurants.adapter.out.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {

    // Existing methods
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

    // Check if a published dish has a pending draft
    boolean existsByPublishedDishUuid(UUID publishedDishId);

    // Count pending drafts for a restaurant
    long countByRestaurant_uuidAndStateAndPublishedDishUuidIsNotNull(
            UUID restaurantId,
            DishState state
    );

    // Find all dishes excluding drafts (for simpler owner view)
    List<DishJpaEntity> findByRestaurant_uuidAndStateNot(UUID restaurantId, DishState state);

    void deleteByPublishedDishUuid(UUID publishedDishId);
}