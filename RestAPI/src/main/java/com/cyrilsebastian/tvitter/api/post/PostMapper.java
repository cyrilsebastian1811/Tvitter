package com.cyrilsebastian.tvitter.api.post;

import org.mapstruct.*;

import java.util.Date;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    @Mappings({
        @Mapping(target = "updatedAt", ignore = true),
        @Mapping(target = "createdAt", expression = "java( new java.util.Date() )"),
    })
    Post postDtoToPost(PostDto dto);

    PostDto postToPostDto(Post entity);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", expression = "java( new java.util.Date() )"),
    })
    void updatePostFromPostDto(PostDto dto, @MappingTarget Post entity);
}
