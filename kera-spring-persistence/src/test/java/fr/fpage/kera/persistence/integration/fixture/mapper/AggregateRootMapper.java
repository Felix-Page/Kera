package fr.fpage.kera.persistence.integration.fixture.mapper;

import fr.fpage.kera.persistence.integration.fixture.domain.AggregateRootDomain;
import fr.fpage.kera.persistence.integration.fixture.entity.AggregateRootEntity;
import fr.fpage.kera.persistence.AbstractEntityMapper;
import fr.fpage.kera.persistence.PersistenceMapperConfig;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = PersistenceMapperConfig.class, uses = ExternalReferenceMapper.class)
public interface AggregateRootMapper extends AbstractEntityMapper<UUID, AggregateRootEntity, AggregateRootDomain> {
}
