package fr.fpage.kera.persistence.integration.fixture.domain;

import fr.fpage.kera.domain.VersionedDomain;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExternalReferenceDomain extends VersionedDomain<UUID> {

    private final String name;

    public ExternalReferenceDomain(
            final UUID id,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final Long version,
            final String name
    ) {
        super(id, createdAt, updatedAt, version);
        this.name = name;
    }

    public static ExternalReferenceDomain create(final String name) {
        return new ExternalReferenceDomain(null, null, null, null, name);
    }

    public String getName() {
        return name;
    }
}
