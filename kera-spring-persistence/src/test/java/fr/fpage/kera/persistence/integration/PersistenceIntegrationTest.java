package fr.fpage.kera.persistence.integration;

import fr.fpage.kera.persistence.JpaEntityResolver;
import fr.fpage.kera.persistence.integration.fixture.domain.AggregateRootDomain;
import fr.fpage.kera.persistence.integration.fixture.mapper.AggregateRootMapperImpl;
import fr.fpage.kera.persistence.integration.fixture.adapter.AggregateRootPortAdapter;
import fr.fpage.kera.persistence.integration.fixture.domain.CompositionChildDomain;
import fr.fpage.kera.persistence.integration.fixture.domain.ExternalReferenceDomain;
import fr.fpage.kera.persistence.integration.fixture.mapper.ExternalReferenceMapperImpl;
import fr.fpage.kera.persistence.integration.fixture.adapter.ExternalReferencePortAdapter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({
        JpaEntityResolver.class,
        ExternalReferencePortAdapter.class,
        ExternalReferenceMapperImpl.class,
        AggregateRootPortAdapter.class,
        AggregateRootMapperImpl.class
})
class PersistenceIntegrationTest {

    @Autowired
    private JpaEntityResolver entityResolver;

    @Autowired
    private ExternalReferencePortAdapter externalReferencePort;

    @Autowired
    private AggregateRootPortAdapter aggregateRootPort;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should auto-configure JpaEntityResolver and persist a VersionedEntity via GenericPortImpl")
    void shouldPersistAndFindSimpleVersionedEntity() {
        assertNotNull(this.entityResolver);

        final ExternalReferenceDomain saved = this.externalReferencePort.save(ExternalReferenceDomain.create("Ref-1"));
        this.entityManager.flush();
        this.entityManager.clear();

        assertNotNull(saved.getId());
        assertEquals(0L, saved.getVersion());
        assertEquals("Ref-1", saved.getName());

        final ExternalReferenceDomain reloaded = this.externalReferencePort.findById(saved.getId()).orElseThrow();
        assertNotNull(reloaded.getCreatedAt());
        assertNotNull(reloaded.getUpdatedAt());
        assertEquals(saved, reloaded);
        assertEquals("Ref-1", reloaded.getName());
    }

    @Test
    @DisplayName("Should persist aggregate with @OneToMany composition and @ManyToOne ReferenceableEntity")
    void shouldPersistAggregateWithCompositionAndReferenceableEntity() {
        final ExternalReferenceDomain externalRef = this.externalReferencePort.save(ExternalReferenceDomain.create("Ref-A"));
        this.entityManager.flush();
        this.entityManager.clear();

        final ExternalReferenceDomain idOnlyRef = new ExternalReferenceDomain(externalRef.getId(), null, null, null, null);
        final AggregateRootDomain aggregate = AggregateRootDomain.create(
                "Aggregate-1",
                idOnlyRef,
                List.of(
                        CompositionChildDomain.create(2, "Second"),
                        CompositionChildDomain.create(1, "First")
                )
        );

        final AggregateRootDomain saved = this.aggregateRootPort.save(aggregate);
        this.entityManager.flush();
        this.entityManager.clear();

        final AggregateRootDomain reloaded = this.aggregateRootPort.findById(saved.getId()).orElseThrow();
        assertEquals(0L, reloaded.getVersion());
        assertEquals("Aggregate-1", reloaded.getName());
        assertEquals(externalRef.getId(), reloaded.getExternalReference().getId());
        assertEquals("Ref-A", reloaded.getExternalReference().getName());
        assertEquals(1, this.externalReferencePort.findAll().size());

        assertEquals(2, reloaded.getChildren().size());
        assertNotNull(reloaded.getChildren().get(0).getId());
        assertEquals(1, reloaded.getChildren().get(0).getChildOrder());
        assertEquals("First", reloaded.getChildren().get(0).getLabel());
        assertEquals(2, reloaded.getChildren().get(1).getChildOrder());
    }

    @Test
    @DisplayName("Should update aggregate, increment @Version, and remove orphan composition children")
    void shouldUpdateAggregateAndIncrementVersion() {
        final ExternalReferenceDomain externalRef = this.externalReferencePort.save(ExternalReferenceDomain.create("Ref-B"));
        final AggregateRootDomain created = this.aggregateRootPort.save(AggregateRootDomain.create(
                "Initial",
                externalRef,
                List.of(
                        CompositionChildDomain.create(1, "Child-1"),
                        CompositionChildDomain.create(2, "Child-2")
                )
        ));
        this.entityManager.flush();
        this.entityManager.clear();

        final AggregateRootDomain toUpdate = new AggregateRootDomain(
                created.getId(),
                created.getCreatedAt(),
                created.getUpdatedAt(),
                created.getVersion(),
                "Updated",
                externalRef,
                List.of(CompositionChildDomain.create(1, "Child-1-Updated"))
        );

        this.aggregateRootPort.save(toUpdate);
        this.entityManager.flush();
        this.entityManager.clear();

        final AggregateRootDomain reloaded = this.aggregateRootPort.findById(created.getId()).orElseThrow();
        assertEquals(1L, reloaded.getVersion());
        assertEquals("Updated", reloaded.getName());
        assertEquals(1, reloaded.getChildren().size());
        assertEquals("Child-1-Updated", reloaded.getChildren().getFirst().getLabel());

        final Long remainingChildRows = this.entityManager
                .createQuery("SELECT COUNT(c) FROM CompositionChildEntity c", Long.class)
                .getSingleResult();
        assertEquals(1L, remainingChildRows);
    }
}
