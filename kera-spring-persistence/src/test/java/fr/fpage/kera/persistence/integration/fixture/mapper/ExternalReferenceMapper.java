package fr.fpage.kera.persistence.integration.fixture.mapper;

import fr.fpage.kera.persistence.integration.fixture.domain.ExternalReferenceDomain;
import fr.fpage.kera.persistence.integration.fixture.entity.ExternalReferenceEntity;
import fr.fpage.kera.persistence.AbstractEntityMapper;
import fr.fpage.kera.persistence.PersistenceMapperConfig;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = PersistenceMapperConfig.class)
public interface ExternalReferenceMapper extends AbstractEntityMapper<UUID, ExternalReferenceEntity, ExternalReferenceDomain> {
}
