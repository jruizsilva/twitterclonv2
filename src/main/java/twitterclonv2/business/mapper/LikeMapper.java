package twitterclonv2.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.*;
import twitterclonv2.domain.dto.like.LikeDto;
import twitterclonv2.domain.entity.LikeEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PostMapper.class, UserMapper.class})
public interface LikeMapper {
    @Named("likeEntityToDto")
    @Mapping(target = "post",
             qualifiedByName = "postEntityToDtoWithoutChildren")
    @Mapping(target = "user",
             qualifiedByName = "userEntityToDtoWithoutChildren")
    LikeDto likeEntityToDtoWithoutChildren(LikeEntity likeEntity);

    @IterableMapping(qualifiedByName = "likeEntityToDto")
    List<LikeDto> likeEntityListToDtoListWithoutChildren(List<LikeEntity> likeEntityList);
}
