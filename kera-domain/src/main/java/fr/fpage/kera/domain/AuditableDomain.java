package fr.fpage.kera.domain;

import java.time.LocalDateTime;

/// Base class for identifiable domain objects that track creation and last modification timestamps.
///
/// @param <ID> the type of the unique identifier
/// @see IdentifiableDomain
/// @see VersionedDomain
public abstract class AuditableDomain<ID> extends IdentifiableDomain<ID> {

    /// The creation timestamp of this domain object.
    protected final LocalDateTime createdAt;

    /// The last modification timestamp of this domain object.
    protected final LocalDateTime updatedAt;

    /// Constructs an auditable domain object.
    ///
    /// @param id        the unique identifier
    /// @param createdAt the creation timestamp
    /// @param updatedAt the last modification timestamp
    protected AuditableDomain(ID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /// Returns the creation timestamp of this domain object.
    ///
    /// @return the creation timestamp
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /// Returns the last modification timestamp of this domain object.
    ///
    /// @return the last modification timestamp
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
