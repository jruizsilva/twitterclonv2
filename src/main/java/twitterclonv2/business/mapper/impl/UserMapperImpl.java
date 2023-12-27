package twitterclonv2.business.mapper.impl;

import org.springframework.stereotype.Component;
import twitterclonv2.business.mapper.UserMapper;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.UserEntity;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                      .id(userEntity.getId())
                      .name(userEntity.getName())
                      .description(userEntity.getDescription())
                      .build();
    }
}
