package twitterclonv2.business.mapper;

import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.UserEntity;

public interface UserMapper {
    UserDto toDto(UserEntity userEntity);
}
