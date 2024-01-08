package twitterclonv2.business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Named("userEntityToDtoWithoutChildren")
    /*@Mapping(target = "postsLiked",
             ignore = true)
    @Mapping(target = "postsCreated",
             ignore = true)
    @Mapping(target = "postsSavedByUser",
             ignore = true)*/
    UserDto userEntityToDtoWithoutChildren(UserEntity userEntity);
}
