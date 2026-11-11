package fr.fpage.kera.persistence;

import fr.fpage.kera.domain.IdentifiableDomain;
import fr.fpage.kera.persistence.entity.IdentifiableEntity;
import fr.fpage.kera.persistence.entity.ReferenceableEntity;

import java.util.List;

/// Base contract for MapStruct mappers converting between a persistence entity and a domain object.
///
/// @param <ID>     the type of the unique identifier
/// @param <ENTITY> the type of the persistence entity
/// @param <DOMAIN> the type of the domain object
/// @see PersistenceMapperConfig
/// @see GenericPortImpl
public interface AbstractEntityMapper<
        ID,
        ENTITY extends IdentifiableEntity<ID> & ReferenceableEntity,
        DOMAIN extends IdentifiableDomain<ID>
        > {

    /// Maps a persistence entity to its corresponding domain object.
    ///
    /// @param entity the persistence entity to map
    /// @return the mapped domain object
    DOMAIN toDomain(ENTITY entity);

    /// Maps a list of persistence entities to a list of domain objects.
    ///
    /// @param entities the list of persistence entities to map
    /// @return the list of mapped domain objects
    List<DOMAIN> toDomain(List<ENTITY> entities);

    /// Maps a domain object to its corresponding persistence entity.
    ///
    /// @param domain the domain object to map
    /// @return the mapped persistence entity
    ENTITY toEntity(DOMAIN domain);
}
