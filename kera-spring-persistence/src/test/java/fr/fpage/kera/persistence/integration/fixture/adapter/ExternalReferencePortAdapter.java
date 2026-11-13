package fr.fpage.kera.persistence.integration.fixture.adapter;

import fr.fpage.kera.persistence.integration.fixture.domain.ExternalReferenceDomain;
import fr.fpage.kera.persistence.integration.fixture.entity.ExternalReferenceEntity;
import fr.fpage.kera.persistence.integration.fixture.mapper.ExternalReferenceMapper;
import fr.fpage.kera.persistence.integration.fixture.repository.ExternalReferenceRepository;
import fr.fpage.kera.persistence.GenericPortImpl;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ExternalReferencePortAdapter extends GenericPortImpl<UUID, ExternalReferenceDomain, ExternalReferenceEntity, ExternalReferenceRepository, ExternalReferenceMapper> {

    public ExternalReferencePortAdapter(final ExternalReferenceRepository repository, final ExternalReferenceMapper mapper) {
        super(repository, mapper);
    }
}
