package be.kdg.keepdishgoing.deliveries.adapter.out.requestProjector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestProjectorJpaRepository extends JpaRepository<RequestProjectorEntity, UUID> {

}
