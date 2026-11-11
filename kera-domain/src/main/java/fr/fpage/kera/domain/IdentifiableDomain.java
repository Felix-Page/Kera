package fr.fpage.kera.domain;

/// Base class for domain objects identified by a unique key.
///
/// @param <ID> the type of the unique identifier
/// @see AuditableDomain
/// @see VersionedDomain
public abstract class IdentifiableDomain<ID> {

    /// The unique identifier of this domain object.
    protected final ID id;

    /// Constructs an identifiable domain object.
    ///
    /// @param id the unique identifier
    protected IdentifiableDomain(ID id) {
        this.id = id;
    }

    /// Returns the unique identifier of this domain object.
    ///
    /// @return the identifier
    public ID getId() {
        return id;
    }
}
