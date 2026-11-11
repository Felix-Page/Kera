package fr.fpage.kera.domain;

import java.time.LocalDateTime;

/// Base class for auditable domain objects that track a version number.
///
/// @param <ID> the type of the unique identifier
/// @see IdentifiableDomain
/// @see AuditableDomain
public abstract class VersionedDomain<ID> extends AuditableDomain<ID> {

    /// The version number of this domain object.
    protected final Long version;

    /// Constructs a versioned domain object.
    ///
    /// @param id        the unique identifier
    /// @param createdAt the creation timestamp
    /// @param updatedAt the last modification timestamp
    /// @param version   the version number
    protected VersionedDomain(ID id, LocalDateTime createdAt, LocalDateTime updatedAt, Long version) {
        super(id, createdAt, updatedAt);
        this.version = version;
    }

    /// Returns the version number of this domain object.
    ///
    /// @return the version number
    public Long getVersion() {
        return version;
    }
}
