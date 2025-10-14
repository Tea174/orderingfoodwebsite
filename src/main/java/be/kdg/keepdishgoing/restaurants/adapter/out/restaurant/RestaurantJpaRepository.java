package be.kdg.keepdishgoing.restaurants.adapter.out.restaurant;

import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {


    List<RestaurantJpaEntity> findByCuisine(TypeOfCuisine typeOfCuisine);

    Optional<RestaurantJpaEntity> findByOwnerId_Uuid(UUID ownerUuid);

    void deleteByUuid(UUID restaurantId);

    @Query("SELECT r FROM RestaurantJpaEntity r WHERE r.uuid = :restaurantId")
    String nameById(@Param("restaurantId") UUID restaurantId);



    Optional<RestaurantJpaEntity> findByName(String name);
}
