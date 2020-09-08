package com.cyrilsebastian.tvitter.api.role;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    Role roleDtoToRole(RoleDto dto);
//    @Mapping(target = "users", ignore = true)
    RoleDto roleToRoleDto(Role entity);
}
