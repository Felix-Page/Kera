package fr.fpage.kera.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VersionedDomainTest {

    private static final class SampleVersionedDomain extends VersionedDomain<UUID> {
        private SampleVersionedDomain(
                final UUID id,
                final LocalDateTime createdAt,
                final LocalDateTime updatedAt,
                final Long version
        ) {
            super(id, createdAt, updatedAt, version);
        }
    }

    @Test
    @DisplayName("Should expose id, audit timestamps, and version")
    void shouldExposeIdTimestampsAndVersion() {
        final UUID id = UUID.randomUUID();
        final LocalDateTime createdAt = LocalDateTime.of(2026, 11, 13, 10, 0);
        final LocalDateTime updatedAt = LocalDateTime.of(2026, 11, 13, 12, 30);

        final SampleVersionedDomain domain = new SampleVersionedDomain(id, createdAt, updatedAt, 3L);

        assertEquals(id, domain.getId());
        assertEquals(createdAt, domain.getCreatedAt());
        assertEquals(updatedAt, domain.getUpdatedAt());
        assertEquals(3L, domain.getVersion());
    }

    @Test
    @DisplayName("Should allow null version for transient domain objects")
    void shouldAllowNullVersion() {
        final SampleVersionedDomain domain = new SampleVersionedDomain(null, null, null, null);

        assertNull(domain.getId());
        assertNull(domain.getVersion());
    }

    @Test
    @DisplayName("Should remain equal when version and timestamps differ for the same id")
    void shouldIgnoreVersionInEquality() {
        final UUID id = UUID.randomUUID();
        final LocalDateTime createdAt = LocalDateTime.of(2026, 11, 13, 10, 0);

        final SampleVersionedDomain v0 = new SampleVersionedDomain(id, createdAt, createdAt, 0L);
        final SampleVersionedDomain v1 = new SampleVersionedDomain(id, createdAt, createdAt.plusHours(1), 1L);

        assertEquals(v0, v1);
        assertEquals(v0.hashCode(), v1.hashCode());
    }
}
