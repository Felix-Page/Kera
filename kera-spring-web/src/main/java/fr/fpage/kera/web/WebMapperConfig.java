package fr.fpage.kera.web;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

/// Shared MapStruct configuration for web layer mappers, enabling Spring component generation.
@MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WebMapperConfig {
}
