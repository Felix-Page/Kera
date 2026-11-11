package fr.fpage.kera.application;

import fr.fpage.kera.domain.IdentifiableDomain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/// Generic secondary port defining standard CRUD operations for a domain object.
///
/// @param <ID>     the type of the unique identifier
/// @param <DOMAIN> the type of the domain object
public interface GenericPort<ID, DOMAIN extends IdentifiableDomain<ID>> {

    /// Finds a domain object by its unique identifier.
    ///
    /// @param id the identifier to look up
    /// @return an [Optional] containing the matching domain object, or empty if none is found
    Optional<DOMAIN> findById(final ID id);

    /// Retrieves all domain objects of this type.
    ///
    /// @return a list of all domain objects
    List<DOMAIN> findAll();

    /// Retrieves all domain objects matching the given identifiers.
    ///
    /// @param ids the collection of identifiers to look up
    /// @return a list of matching domain objects
    List<DOMAIN> findAllById(final Collection<ID> ids);

    /// Saves a domain object (creates a new entry or updates an existing one).
    ///
    /// @param domain the domain object to save
    /// @return the saved domain object
    DOMAIN save(final DOMAIN domain);

    /// Saves a collection of domain objects.
    ///
    /// @param domains the domain objects to save
    /// @return a list of the saved domain objects
    List<DOMAIN> saveAll(final Collection<DOMAIN> domains);

    /// Deletes the given domain object.
    ///
    /// @param domain the domain object to delete
    void delete(final DOMAIN domain);

    /// Deletes the domain object with the given identifier.
    ///
    /// @param id the identifier of the domain object to delete
    void deleteById(final ID id);
}
