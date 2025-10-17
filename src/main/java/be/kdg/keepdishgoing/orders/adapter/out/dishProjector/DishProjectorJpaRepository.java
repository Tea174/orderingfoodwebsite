package be.kdg.keepdishgoing.orders.adapter.out.dishProjector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishProjectorJpaRepository extends JpaRepository<DishProjectorEntity, UUID> {
}