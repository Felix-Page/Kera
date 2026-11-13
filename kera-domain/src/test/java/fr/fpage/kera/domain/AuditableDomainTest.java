package fr.fpage.kera.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuditableDomainTest {

    private static final class SampleAuditableDomain extends AuditableDomain<UUID> {
        private SampleAuditableDomain(final UUID id, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
            super(id, createdAt, updatedAt);
        }
    }

    @Test
    @DisplayName("Should expose id and audit timestamps")
    void shouldExposeIdAndAuditTimestamps() {
        final UUID id = UUID.randomUUID();
        final LocalDateTime createdAt = LocalDateTime.of(2026, 11, 13, 10, 0);
        final LocalDateTime updatedAt = LocalDateTime.of(2026, 11, 13, 12, 30);

        final SampleAuditableDomain domain = new SampleAuditableDomain(id, createdAt, updatedAt);

        assertEquals(id, domain.getId());
        assertEquals(createdAt, domain.getCreatedAt());
        assertEquals(updatedAt, domain.getUpdatedAt());
    }

    @Test
    @DisplayName("Should allow null metadata for transient domain objects")
    void shouldAllowNullMetadata() {
        final SampleAuditableDomain domain = new SampleAuditableDomain(null, null, null);

        assertNull(domain.getId());
        assertNull(domain.getCreatedAt());
        assertNull(domain.getUpdatedAt());
    }

    @Test
    @DisplayName("Should remain equal when audit timestamps differ for the same id")
    void shouldIgnoreAuditTimestampsInEquality() {
        final UUID id = UUID.randomUUID();
        final SampleAuditableDomain initial = new SampleAuditableDomain(
                id,
                LocalDateTime.of(2026, 11, 13, 10, 0),
                LocalDateTime.of(2026, 11, 13, 10, 0)
        );
        final SampleAuditableDomain modified = new SampleAuditableDomain(
                id,
                LocalDateTime.of(2026, 11, 13, 10, 0),
                LocalDateTime.of(2026, 11, 13, 15, 45)
        );

        assertEquals(initial, modified);
        assertEquals(initial.hashCode(), modified.hashCode());
    }
}
