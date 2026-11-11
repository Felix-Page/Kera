package fr.fpage.kera.persistence;

import fr.fpage.kera.application.GenericPort;
import fr.fpage.kera.domain.IdentifiableDomain;
import fr.fpage.kera.persistence.entity.IdentifiableEntity;
import fr.fpage.kera.persistence.entity.ReferenceableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/// Base JPA implementation of [GenericPort] delegating persistence operations to a
/// Spring Data [JpaRepository] and converting models through an [AbstractEntityMapper].
///
/// @param <ID>         the type of the unique identifier
/// @param <DOMAIN>     the type of the domain object
/// @param <ENTITY>     the type of the persistence entity
/// @param <REPOSITORY> the type of the Spring Data JPA repository
/// @param <MAPPER>     the type of the entity-domain mapper
/// @see GenericPort
/// @see AbstractEntityMapper
public abstract class GenericPortImpl<
        ID,
        DOMAIN extends IdentifiableDomain<ID>,
        ENTITY extends IdentifiableEntity<ID> & ReferenceableEntity,
        REPOSITORY extends JpaRepository<ENTITY, ID>,
        MAPPER extends AbstractEntityMapper<ID, ENTITY, DOMAIN>
        > implements GenericPort<ID, DOMAIN> {

    /// The underlying Spring Data JPA repository.
    protected final REPOSITORY repository;

    /// The mapper converting between persistence entities and domain objects.
    protected final MAPPER mapper;

    /// Constructs a persistence adapter with the given repository and mapper.
    ///
    /// @param repository the Spring Data JPA repository
    /// @param mapper     the entity-domain mapper
    public GenericPortImpl(REPOSITORY repository, MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<DOMAIN> findById(ID id) {
        return this.repository.findById(id).map(this.mapper::toDomain);
    }

    @Override
    public List<DOMAIN> findAll() {
        return this.repository.findAll().stream().map(this.mapper::toDomain).toList();
    }

    @Override
    public List<DOMAIN> findAllById(Collection<ID> ids) {
        return this.repository.findAllById(ids).stream().map(this.mapper::toDomain).toList();
    }

    @Override
    public DOMAIN save(DOMAIN domain) {
        return this.mapper.toDomain(this.repository.save(this.mapper.toEntity(domain)));
    }

    @Override
    public List<DOMAIN> saveAll(Collection<DOMAIN> domains) {
        return this.repository.saveAll(domains.stream().map(this.mapper::toEntity).toList()).stream().map(this.mapper::toDomain).toList();
    }

    @Override
    public void delete(DOMAIN domain) {
        this.repository.delete(this.mapper.toEntity(domain));
    }
}
