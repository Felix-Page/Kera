package fr.fpage.kera.persistence;

import fr.fpage.kera.domain.IdentifiableDomain;
import fr.fpage.kera.persistence.entity.IdentifiableEntity;
import fr.fpage.kera.persistence.entity.ReferenceableEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

/// MapStruct component that resolves a [ReferenceableEntity] reference from an [IdentifiableDomain]
/// using [EntityManager#getReference(Class, Object)].
///
/// @see ReferenceableEntity
/// @see PersistenceMapperConfig
@Component
public class JpaEntityResolver {

    @PersistenceContext
    private EntityManager entityManager;

    /// Returns a JPA reference proxy for the given domain object's identifier,
    /// or `null` if the domain object or its identifier is `null`.
    ///
    /// @param <ENTITY>   the target persistence entity type
    /// @param <DOMAIN>   the source domain object type
    /// @param domain     the domain object holding the identifier
    /// @param entityType the target persistence entity class
    /// @return a reference proxy of `entityType`, or `null`
    public <ENTITY extends IdentifiableEntity<?> & ReferenceableEntity, DOMAIN extends IdentifiableDomain<?>> ENTITY resolveReference(DOMAIN domain, @TargetType Class<ENTITY> entityType) {
        if (domain == null || domain.getId() == null) {
            return null;
        }
        return this.entityManager.getReference(entityType, domain.getId());
    }
}
