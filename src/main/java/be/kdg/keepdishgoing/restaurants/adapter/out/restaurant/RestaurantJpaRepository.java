package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {

    Optional<RestaurantJpaEntity> findByOwnerId_Uuid(UUID ownerUuid);

    void deleteByUuid(UUID restaurantId);

    @Query("SELECT r FROM RestaurantJpaEntity r LEFT JOIN FETCH r.dishes WHERE r.uuid = :id")
    Optional<RestaurantJpaEntity> findByIdWithDishes(@Param("id") UUID id);

    Optional<RestaurantJpaEntity> findByName(String name);
}
