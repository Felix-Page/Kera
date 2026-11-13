package fr.fpage.kera.persistence.integration.fixture.adapter;

import fr.fpage.kera.persistence.integration.fixture.domain.AggregateRootDomain;
import fr.fpage.kera.persistence.integration.fixture.entity.AggregateRootEntity;
import fr.fpage.kera.persistence.integration.fixture.mapper.AggregateRootMapper;
import fr.fpage.kera.persistence.integration.fixture.repository.AggregateRootRepository;
import fr.fpage.kera.persistence.GenericPortImpl;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AggregateRootPortAdapter extends GenericPortImpl<UUID, AggregateRootDomain, AggregateRootEntity, AggregateRootRepository, AggregateRootMapper> {

    public AggregateRootPortAdapter(final AggregateRootRepository repository, final AggregateRootMapper mapper) {
        super(repository, mapper);
    }
}
