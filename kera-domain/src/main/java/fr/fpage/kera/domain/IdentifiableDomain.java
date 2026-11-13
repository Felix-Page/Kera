package fr.fpage.kera.domain;

import java.util.Objects;

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

    /// Compares two domain objects by runtime class and non-null identifier.
    /// Two transient instances with a `null` identifier are equal only by reference identity.
    ///
    /// @param o the object to compare with
    /// @return `true` if both objects have the same runtime class and equal non-null identifiers, or are the same instance
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IdentifiableDomain<?> that = (IdentifiableDomain<?>) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    /// Returns a hash code value based on the identifier.
    ///
    /// @return the hash code of `id`, or `0` if `id` is `null`
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
