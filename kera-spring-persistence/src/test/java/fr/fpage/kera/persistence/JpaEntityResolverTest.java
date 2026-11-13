package fr.fpage.kera.persistence;

import fr.fpage.kera.domain.IdentifiableDomain;
import fr.fpage.kera.persistence.entity.IdentifiableEntity;
import fr.fpage.kera.persistence.entity.ReferenceableEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaEntityResolverTest {

    private static final class SampleDomain extends IdentifiableDomain<UUID> {
        private SampleDomain(final UUID id) {
            super(id);
        }
    }

    private static final class SampleEntity implements IdentifiableEntity<UUID>, ReferenceableEntity {
        private UUID id;

        private SampleEntity(final UUID id) {
            this.id = id;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public void setId(final UUID id) {
            this.id = id;
        }
    }

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private JpaEntityResolver resolver;

    @Test
    @DisplayName("Should return null without calling EntityManager when domain is null")
    void shouldReturnNullWhenDomainIsNull() {
        final SampleEntity result = this.resolver.resolveReference(null, SampleEntity.class);

        assertNull(result);
        verifyNoInteractions(this.entityManager);
    }

    @Test
    @DisplayName("Should return null without calling EntityManager when domain id is null")
    void shouldReturnNullWhenDomainIdIsNull() {
        final SampleDomain domain = new SampleDomain(null);

        final SampleEntity result = this.resolver.resolveReference(domain, SampleEntity.class);

        assertNull(result);
        verifyNoInteractions(this.entityManager);
    }

    @Test
    @DisplayName("Should delegate to EntityManager#getReference when domain id is non-null")
    void shouldReturnReferenceProxyWhenDomainIdIsPresent() {
        final UUID id = UUID.randomUUID();
        final SampleDomain domain = new SampleDomain(id);
        final SampleEntity expectedProxy = new SampleEntity(id);

        when(this.entityManager.getReference(SampleEntity.class, id)).thenReturn(expectedProxy);

        final SampleEntity result = this.resolver.resolveReference(domain, SampleEntity.class);

        assertSame(expectedProxy, result);
        verify(this.entityManager).getReference(SampleEntity.class, id);
        verifyNoMoreInteractions(this.entityManager);
    }
}
