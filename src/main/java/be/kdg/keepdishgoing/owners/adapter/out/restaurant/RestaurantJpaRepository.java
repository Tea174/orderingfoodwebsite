package be.kdg.keepdishgoing.owners.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Integer> {
    Optional<RestaurantJpaEntity> findByName(String name);
    Optional<RestaurantJpaEntity> findByOwner(UUID id);
    Optional<RestaurantJpaEntity> findByTag(String tag);
    Optional<RestaurantJpaEntity> findById(UUID id);
    Optional<RestaurantJpaEntity> deleteById(UUID id);
}
