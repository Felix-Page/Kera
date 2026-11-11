package fr.fpage.kera.persistence.entity;

import java.time.LocalDateTime;

/// Contract for identifiable persistence entities that track creation and last modification timestamps.
///
/// @param <ID> the type of the unique identifier
/// @see IdentifiableEntity
/// @see VersionedEntity
public interface AuditableEntity<ID> extends IdentifiableEntity<ID> {

    /// Returns the creation timestamp of this entity.
    ///
    /// @return the creation timestamp
    LocalDateTime getCreatedAt();

    /// Sets the creation timestamp of this entity.
    ///
    /// @param createdAt the creation timestamp to set
    void setCreatedAt(LocalDateTime createdAt);

    /// Returns the last modification timestamp of this entity.
    ///
    /// @return the last modification timestamp
    LocalDateTime getUpdatedAt();

    /// Sets the last modification timestamp of this entity.
    ///
    /// @param updatedAt the last modification timestamp to set
    void setUpdatedAt(LocalDateTime updatedAt);
}
