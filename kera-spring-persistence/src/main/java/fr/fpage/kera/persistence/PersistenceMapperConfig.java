package fr.fpage.kera.persistence;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

/// Shared MapStruct configuration for persistence entity mappers, enabling Spring component
/// generation and registering [JpaEntityResolver].
///
/// @see AbstractEntityMapper
/// @see JpaEntityResolver
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = JpaEntityResolver.class
)
public interface PersistenceMapperConfig {
}
