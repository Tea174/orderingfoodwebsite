package be.kdg.keepdishgoing.owners.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Integer> {
    Optional<RestaurantJpaEntity> findByName(String name);
    Optional<RestaurantJpaEntity> findByOwner(Integer id);
    Optional<RestaurantJpaEntity> findByTag(String tag);
}
