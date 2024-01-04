package twitterclonv2.business.mapper;

import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

public interface Mapper {
    PostDto postEntityToDto(PostEntity postEntity);
    UserDto userEntityToDto(UserEntity userEntity);
}
