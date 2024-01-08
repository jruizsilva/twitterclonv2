package twitterclonv2.business.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.mapper.PostMapper;
import twitterclonv2.business.mapper.UserMapper;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class MapperImpl implements Mapper {
    private final PostMapper postMapper;
    private final UserMapper userMapper;

    @Override
    public PostDto postEntityToDto(PostEntity postEntity) {
        PostDto postDto = postMapper.postEntityToDtoWithoutChildren(postEntity);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM 'at' HH:mm");
        String formattedDate = postEntity.getCreatedAt()
                                         .format(formatter);
        postDto.setCreatedAt(formattedDate);
        postDto.setUser(userMapper.userEntityToDtoWithoutChildren(postEntity.getUser()));
        return postDto;
    }

    @Override
    public UserDto userEntityToDto(UserEntity userEntity) {
        UserDto userDto = userMapper.userEntityToDtoWithoutChildren(userEntity);
        userDto.setPostsCreated(postMapper.postEntityListToDtoListWithoutChildren(userEntity.getPostsCreated()));
        return userDto;
    }
}
