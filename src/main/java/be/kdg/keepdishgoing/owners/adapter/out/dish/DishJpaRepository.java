package be.kdg.keepdishgoing.owners.adapter.out.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishJpaRepository extends JpaRepository<DishJpaEntity, Integer> {
    Optional<DishJpaEntity> findByDishId(UUID dishId);
    List<DishJpaEntity> findByRestaurant_RestaurantId(UUID restaurantId);
    void deleteByDishId(UUID dishId);
}
