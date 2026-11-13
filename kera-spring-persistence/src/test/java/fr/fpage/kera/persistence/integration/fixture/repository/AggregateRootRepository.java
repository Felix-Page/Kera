package fr.fpage.kera.persistence.integration.fixture.repository;

import fr.fpage.kera.persistence.integration.fixture.entity.AggregateRootEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AggregateRootRepository extends JpaRepository<AggregateRootEntity, UUID> {
}
