package com.cyrilsebastian.tvitter.api.profile;

import com.cyrilsebastian.tvitter.api.user.UserMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UserMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {
    @Mappings({
            @Mapping(target="latitude", expression = "java( (float)Math.toRadians( dto.getLatitude() ))"),
            @Mapping(target="longitude", expression = "java( (float)Math.toRadians( dto.getLongitude() ))")
    })
    Profile profileDtoToProfile(ProfileDto dto);

    @Mapping(target = "user", ignore = true)
    ProfileDto profileToProfileDto(Profile entity);

    @Mappings({
            @Mapping(target="latitude", expression = "java( (float)Math.toRadians( dto.getLatitude() ))"),
            @Mapping(target="longitude", expression = "java( (float)Math.toRadians( dto.getLongitude() ))"),
            @Mapping(target = "user", ignore = true)
    })
    void updateUserFromUserDto(ProfileDto dto, @MappingTarget Profile entity);
}
