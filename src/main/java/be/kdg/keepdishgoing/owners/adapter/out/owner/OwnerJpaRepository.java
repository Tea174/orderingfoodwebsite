package be.kdg.keepdishgoing.owners.adapter.out.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerJpaEntity, UUID> {
    Optional<OwnerJpaEntity> findByEmail(String email);
}