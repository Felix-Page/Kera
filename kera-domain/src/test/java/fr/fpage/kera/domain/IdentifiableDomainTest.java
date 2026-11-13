package fr.fpage.kera.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdentifiableDomainTest {

    private static final class SampleDomain extends IdentifiableDomain<UUID> {
        private final String label;

        private SampleDomain(final UUID id, final String label) {
            super(id);
            this.label = label;
        }
    }

    private static final class OtherDomain extends IdentifiableDomain<UUID> {
        private OtherDomain(final UUID id) {
            super(id);
        }
    }

    @Test
    @DisplayName("Should expose identifier passed to constructor")
    void shouldExposeIdentifier() {
        final UUID id = UUID.randomUUID();
        final SampleDomain domain = new SampleDomain(id, "test");

        assertEquals(id, domain.getId());
        assertNull(new SampleDomain(null, "transient").getId());
    }

    @Test
    @DisplayName("Should be equal to itself even when id is null")
    void shouldBeEqualToSelfWhenIdIsNull() {
        final SampleDomain transientDomain = new SampleDomain(null, "transient");

        assertEquals(transientDomain, transientDomain);
    }

    @Test
    @DisplayName("Should not be equal to null or an instance of another class with same id")
    void shouldNotBeEqualToNullOrDifferentClass() {
        final UUID id = UUID.randomUUID();
        final SampleDomain sample = new SampleDomain(id, "sample");
        final OtherDomain other = new OtherDomain(id);

        assertNotEquals(null, sample);
        assertNotEquals(sample, other);
    }

    @Test
    @DisplayName("Should be equal and share hashCode when same class and same non-null id regardless of other fields")
    void shouldBeEqualWhenSameClassAndSameNonNullId() {
        final UUID id = UUID.randomUUID();
        final SampleDomain first = new SampleDomain(id, "initial");
        final SampleDomain second = new SampleDomain(id, "updated");

        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    @DisplayName("Should not be equal when ids are different")
    void shouldNotBeEqualWhenIdsDiffer() {
        final SampleDomain first = new SampleDomain(UUID.randomUUID(), "same-label");
        final SampleDomain second = new SampleDomain(UUID.randomUUID(), "same-label");

        assertNotEquals(first, second);
    }

    @Test
    @DisplayName("Should not consider two distinct transient instances with null id as equal")
    void shouldNotBeEqualWhenBothIdsAreNull() {
        final SampleDomain first = new SampleDomain(null, "first");
        final SampleDomain second = new SampleDomain(null, "second");

        assertNotEquals(first, second);
        assertEquals(0, first.hashCode());
    }

    @Test
    @DisplayName("Should not be equal when only one instance has a null id")
    void shouldNotBeEqualWhenOneIdIsNull() {
        final SampleDomain transientDomain = new SampleDomain(null, "a");
        final SampleDomain persistedDomain = new SampleDomain(UUID.randomUUID(), "a");

        assertNotEquals(transientDomain, persistedDomain);
        assertNotEquals(persistedDomain, transientDomain);
    }

    @Test
    @DisplayName("Should deduplicate persisted entities by id in a Set while keeping distinct transient instances")
    void shouldBehaveProperlyInSet() {
        final UUID sharedId = UUID.randomUUID();
        final SampleDomain persistedA = new SampleDomain(sharedId, "v1");
        final SampleDomain persistedB = new SampleDomain(sharedId, "v2");
        final SampleDomain transientA = new SampleDomain(null, "t1");
        final SampleDomain transientB = new SampleDomain(null, "t2");

        final Set<SampleDomain> set = new HashSet<>();
        set.add(persistedA);
        set.add(persistedB);
        set.add(transientA);
        set.add(transientB);

        assertEquals(3, set.size());
        assertTrue(set.contains(persistedA));
        assertTrue(set.contains(transientA));
        assertTrue(set.contains(transientB));
    }
}
