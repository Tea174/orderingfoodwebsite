package be.kdg.keepdishgoing.owners.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishJpaRepository extends JpaRepository<DishJpaEntity, Integer> {
    Optional<DishJpaEntity> findByRestaurant(Integer id);
}
