package fr.fpage.kera.persistence.entity;

/// Contract for persistence entities identified by a unique key.
///
/// @param <ID> the type of the unique identifier
/// @see AuditableEntity
/// @see VersionedEntity
public interface IdentifiableEntity<ID> {

    /// Returns the unique identifier of this entity.
    ///
    /// @return the identifier
    ID getId();

    /// Sets the unique identifier of this entity.
    ///
    /// @param id the identifier to set
    void setId(ID id);
}
