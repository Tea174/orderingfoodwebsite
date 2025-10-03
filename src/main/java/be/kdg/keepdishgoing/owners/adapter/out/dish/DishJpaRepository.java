package be.kdg.keepdishgoing.owners.adapter.out.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DishJpaRepository extends JpaRepository<DishJpaEntity, Integer> {
    Optional<DishJpaEntity> findByDishId(UUID dishId);
    List<DishJpaEntity> findByRestaurant_RestaurantId(UUID restaurantId);
    void deleteByDishId(UUID dishId);
}
