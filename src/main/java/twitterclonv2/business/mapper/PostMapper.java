package twitterclonv2.business.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    @Named("postEntityToDtoWithoutChildren")
   /* @Mapping(target = "likedByUsers",
             ignore = true)
    @Mapping(target = "author",
             ignore = true)*/
    PostDto postEntityToDtoWithoutChildren(PostEntity postEntity);

    @IterableMapping(qualifiedByName = "postEntityToDtoWithoutChildren")
    List<PostDto> postEntityListToDtoListWithoutChildren(List<PostEntity> postEntityList);
}
