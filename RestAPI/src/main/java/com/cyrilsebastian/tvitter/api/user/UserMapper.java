package com.cyrilsebastian.tvitter.api.user;

import com.cyrilsebastian.tvitter.api.role.RoleMapper;
import org.mapstruct.*;

//@Mapper(componentModel = "spring", uses = {RoleMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//public abstract class UserMapper {
//    @Autowired
//    public PasswordEncoder passwordEncoder;
//
//    @Mapping(target = "password", expression = "java( null )")
//    abstract UserDto userToUserDto(User entity);
//
//    @Mapping(target = "password", expression = "java( passwordEncoder.encode(dto.getPassword()) )")
//    abstract User userDtoToUser(UserDto dto);
//
//    @Mapping(target = "roles", ignore = true)
//    abstract void updateUserFromUserDto(UserDto dto, @MappingTarget User entity);
//
//    @AfterMapping
//    void addCreatedAt(@MappingTarget User entity) {
//        if(entity.getCreatedAt()==null) entity.setCreatedAt(new Date());
//        else entity.setUpdatedAt(new Date());
//    }
//}

@Mapper(componentModel = "spring", uses = {RoleMapper.class, PasswordMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "password", expression = "java( null )")
    UserDto userToUserDto(User entity);

    @Mappings({
        @Mapping(target = "password", qualifiedByName = { "AddOnMapper", "HashPassword" }),
        @Mapping(target = "updatedAt", ignore = true)
    })
    User userDtoToUser(UserDto dto);


    @Mappings({
            @Mapping(target = "password", qualifiedByName = { "AddOnMapper", "HashPassword" }),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "roles", ignore = true)
    })
    void updateUserFromUserDto(UserDto dto, @MappingTarget User entity);
}
