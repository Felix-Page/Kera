package fr.fpage.kera.persistence.integration.fixture.domain;

import fr.fpage.kera.domain.VersionedDomain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AggregateRootDomain extends VersionedDomain<UUID> {

    private final String name;
    private final ExternalReferenceDomain externalReference;
    private final List<CompositionChildDomain> children;

    public AggregateRootDomain(
            final UUID id,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final Long version,
            final String name,
            final ExternalReferenceDomain externalReference,
            final List<CompositionChildDomain> children
    ) {
        super(id, createdAt, updatedAt, version);
        this.name = name;
        this.externalReference = externalReference;
        this.children = List.copyOf(children);
    }

    public static AggregateRootDomain create(
            final String name,
            final ExternalReferenceDomain externalReference,
            final List<CompositionChildDomain> children
    ) {
        return new AggregateRootDomain(null, null, null, null, name, externalReference, children);
    }

    public String getName() {
        return name;
    }

    public ExternalReferenceDomain getExternalReference() {
        return externalReference;
    }

    public List<CompositionChildDomain> getChildren() {
        return children;
    }
}
