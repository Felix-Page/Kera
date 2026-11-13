package fr.fpage.kera.persistence.integration.fixture.repository;

import fr.fpage.kera.persistence.integration.fixture.entity.ExternalReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExternalReferenceRepository extends JpaRepository<ExternalReferenceEntity, UUID> {
}
