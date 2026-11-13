package fr.fpage.kera.persistence;

import fr.fpage.kera.domain.IdentifiableDomain;
import fr.fpage.kera.persistence.entity.IdentifiableEntity;
import fr.fpage.kera.persistence.entity.ReferenceableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenericPortImplTest {

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

    private interface SampleRepository extends JpaRepository<SampleEntity, UUID> {
    }

    private interface SampleMapper extends AbstractEntityMapper<UUID, SampleEntity, SampleDomain> {
    }

    private static final class SamplePortAdapter extends GenericPortImpl<UUID, SampleDomain, SampleEntity, SampleRepository, SampleMapper> {
        private SamplePortAdapter(final SampleRepository repository, final SampleMapper mapper) {
            super(repository, mapper);
        }
    }

    @Mock
    private SampleRepository repository;

    @Mock
    private SampleMapper mapper;

    private SamplePortAdapter port;

    @BeforeEach
    void setUp() {
        this.port = new SamplePortAdapter(this.repository, this.mapper);
    }

    @Nested
    @DisplayName("findAll")
    class FindAll {

        @Test
        @DisplayName("Should return mapped domain objects preserving repository order")
        void shouldReturnMappedDomainsInOrder() {
            final UUID id1 = UUID.randomUUID();
            final UUID id2 = UUID.randomUUID();
            final SampleEntity entity1 = new SampleEntity(id1);
            final SampleEntity entity2 = new SampleEntity(id2);
            final SampleDomain domain1 = new SampleDomain(id1);
            final SampleDomain domain2 = new SampleDomain(id2);

            when(repository.findAll()).thenReturn(List.of(entity1, entity2));
            when(mapper.toDomain(entity1)).thenReturn(domain1);
            when(mapper.toDomain(entity2)).thenReturn(domain2);

            final List<SampleDomain> result = port.findAll();

            assertEquals(List.of(domain1, domain2), result);
            verify(repository).findAll();
            verify(mapper).toDomain(entity1);
            verify(mapper).toDomain(entity2);
            verifyNoMoreInteractions(repository, mapper);
        }

        @Test
        @DisplayName("Should return empty list without calling mapper when repository is empty")
        void shouldReturnEmptyListWhenRepositoryIsEmpty() {
            when(repository.findAll()).thenReturn(List.of());

            final List<SampleDomain> result = port.findAll();

            assertTrue(result.isEmpty());
            verify(repository).findAll();
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @DisplayName("Should return mapped domain object when entity exists")
        void shouldReturnMappedDomainWhenFound() {
            final UUID id = UUID.randomUUID();
            final SampleEntity entity = new SampleEntity(id);
            final SampleDomain domain = new SampleDomain(id);

            when(repository.findById(id)).thenReturn(Optional.of(entity));
            when(mapper.toDomain(entity)).thenReturn(domain);

            final Optional<SampleDomain> result = port.findById(id);

            assertEquals(Optional.of(domain), result);
            verify(repository).findById(id);
            verify(mapper).toDomain(entity);
            verifyNoMoreInteractions(repository, mapper);
        }

        @Test
        @DisplayName("Should return empty optional without calling mapper when entity is missing")
        void shouldReturnEmptyWhenNotFound() {
            final UUID id = UUID.randomUUID();
            when(repository.findById(id)).thenReturn(Optional.empty());

            final Optional<SampleDomain> result = port.findById(id);

            assertTrue(result.isEmpty());
            verify(repository).findById(id);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    @DisplayName("findAllById")
    class FindAllById {

        @Test
        @DisplayName("Should return mapped domain objects for matching ids")
        void shouldReturnMappedDomains() {
            final UUID id1 = UUID.randomUUID();
            final UUID id2 = UUID.randomUUID();
            final Set<UUID> ids = Set.of(id1, id2);
            final SampleEntity entity1 = new SampleEntity(id1);
            final SampleEntity entity2 = new SampleEntity(id2);
            final SampleDomain domain1 = new SampleDomain(id1);
            final SampleDomain domain2 = new SampleDomain(id2);

            when(repository.findAllById(ids)).thenReturn(List.of(entity1, entity2));
            when(mapper.toDomain(entity1)).thenReturn(domain1);
            when(mapper.toDomain(entity2)).thenReturn(domain2);

            final List<SampleDomain> result = port.findAllById(ids);

            assertEquals(List.of(domain1, domain2), result);
            verify(repository).findAllById(ids);
            verify(mapper).toDomain(entity1);
            verify(mapper).toDomain(entity2);
            verifyNoMoreInteractions(repository, mapper);
        }

        @Test
        @DisplayName("Should return empty list without calling mapper when no entity matches")
        void shouldReturnEmptyListWhenNoneFound() {
            final List<UUID> ids = List.of(UUID.randomUUID());
            when(repository.findAllById(ids)).thenReturn(List.of());

            final List<SampleDomain> result = port.findAllById(ids);

            assertTrue(result.isEmpty());
            verify(repository).findAllById(ids);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        @DisplayName("Should map domain to entity, persist it, and map saved entity back to domain")
        void shouldSaveAndReturnMappedDomain() {
            final UUID id = UUID.randomUUID();
            final SampleDomain input = new SampleDomain(null);
            final SampleEntity toPersist = new SampleEntity(null);
            final SampleEntity persisted = new SampleEntity(id);
            final SampleDomain output = new SampleDomain(id);

            when(mapper.toEntity(input)).thenReturn(toPersist);
            when(repository.save(toPersist)).thenReturn(persisted);
            when(mapper.toDomain(persisted)).thenReturn(output);

            final SampleDomain result = port.save(input);

            assertSame(output, result);
            verify(mapper).toEntity(input);
            verify(repository).save(toPersist);
            verify(mapper).toDomain(persisted);
            verifyNoMoreInteractions(repository, mapper);
        }
    }

    @Nested
    @DisplayName("saveAll")
    class SaveAll {

        @Test
        @DisplayName("Should map all domains, persist them, and return mapped domains in order")
        void shouldSaveAllAndReturnMappedDomains() {
            final UUID id1 = UUID.randomUUID();
            final UUID id2 = UUID.randomUUID();
            final SampleDomain input1 = new SampleDomain(null);
            final SampleDomain input2 = new SampleDomain(null);
            final SampleEntity toPersist1 = new SampleEntity(null);
            final SampleEntity toPersist2 = new SampleEntity(null);
            final SampleEntity persisted1 = new SampleEntity(id1);
            final SampleEntity persisted2 = new SampleEntity(id2);
            final SampleDomain output1 = new SampleDomain(id1);
            final SampleDomain output2 = new SampleDomain(id2);

            when(mapper.toEntity(input1)).thenReturn(toPersist1);
            when(mapper.toEntity(input2)).thenReturn(toPersist2);
            when(repository.saveAll(List.of(toPersist1, toPersist2))).thenReturn(List.of(persisted1, persisted2));
            when(mapper.toDomain(persisted1)).thenReturn(output1);
            when(mapper.toDomain(persisted2)).thenReturn(output2);

            final List<SampleDomain> result = port.saveAll(List.of(input1, input2));

            assertEquals(List.of(output1, output2), result);
            verify(mapper).toEntity(input1);
            verify(mapper).toEntity(input2);
            verify(repository).saveAll(List.of(toPersist1, toPersist2));
            verify(mapper).toDomain(persisted1);
            verify(mapper).toDomain(persisted2);
            verifyNoMoreInteractions(repository, mapper);
        }

        @Test
        @DisplayName("Should handle empty collection without calling mapper")
        void shouldHandleEmptyCollection() {
            when(repository.saveAll(List.of())).thenReturn(List.of());

            final List<SampleDomain> result = port.saveAll(List.of());

            assertTrue(result.isEmpty());
            verify(repository).saveAll(List.of());
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("Should map domain to entity and delegate deletion to repository")
        void shouldMapAndDeleteEntity() {
            final UUID id = UUID.randomUUID();
            final SampleDomain domain = new SampleDomain(id);
            final SampleEntity entity = new SampleEntity(id);

            when(mapper.toEntity(domain)).thenReturn(entity);

            port.delete(domain);

            verify(mapper).toEntity(domain);
            verify(repository).delete(entity);
            verifyNoMoreInteractions(repository, mapper);
        }
    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {

        @Test
        @DisplayName("Should delegate deletion by id directly to repository without calling mapper")
        void shouldDelegateDeleteById() {
            final UUID id = UUID.randomUUID();

            port.deleteById(id);

            verify(repository).deleteById(id);
            verifyNoMoreInteractions(repository);
            verifyNoInteractions(mapper);
        }
    }
}
