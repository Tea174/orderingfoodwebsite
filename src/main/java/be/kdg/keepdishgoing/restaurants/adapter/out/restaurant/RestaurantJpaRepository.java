package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {
    Optional<RestaurantJpaEntity> findByName(String name);
    @Query("SELECT r FROM RestaurantJpaEntity r WHERE r.ownerId.uuid = :ownerUuid")
    Optional<RestaurantJpaEntity> findByOwnerId(@Param("ownerUuid") UUID ownerUuid);
    Optional<RestaurantJpaEntity> findByUuid(UUID restaurantId);
    void deleteByUuid(UUID restaurantId);
}
