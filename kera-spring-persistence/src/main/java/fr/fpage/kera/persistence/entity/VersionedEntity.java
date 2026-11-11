package fr.fpage.kera.persistence.entity;

/// Contract for auditable persistence entities that track an optimistic locking version number.
///
/// @param <ID> the type of the unique identifier
/// @see IdentifiableEntity
/// @see AuditableEntity
public interface VersionedEntity<ID> extends AuditableEntity<ID> {

    /// Returns the optimistic locking version number of this entity.
    ///
    /// @return the version number
    Long getVersion();

    /// Sets the optimistic locking version number of this entity.
    ///
    /// @param version the version number to set
    void setVersion(Long version);
}
