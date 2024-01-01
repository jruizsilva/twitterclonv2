package twitterclonv2.business.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.domain.dto.like.LikeDto;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class MapperImpl implements Mapper {
    @Override
    public PostDto postEntityToDto(PostEntity postEntity,
                                   boolean includeAuthor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM 'at' HH:mm");
        String formattedDate = postEntity.getCreatedAt()
                                         .format(formatter);
        PostDto postDto = PostDto.builder()
                                 .id(postEntity.getId())
                                 .content(postEntity.getContent())
                                 .createdAt(formattedDate)
                                 .build();
        if (includeAuthor) {
            postDto.setAuthor(this.userEntityToDto(postEntity.getAuthor(),
                                                   false));
        }
        return postDto;
    }

    @Override
    public UserDto userEntityToDto(UserEntity userEntity,
                                   boolean includePosts) {
        UserDto userDto = UserDto.builder()
                                 .id(userEntity.getId())
                                 .name(userEntity.getName())
                                 .username(userEntity.getUsername())
                                 .description(userEntity.getDescription())
                                 .build();
        if (includePosts) {
            userDto.setPosts(userEntity.getPostsCreated()
                                       .stream()
                                       .map(postEntity -> this.postEntityToDto(postEntity,
                                                                               false))
                                       .toList());
        }
        return userDto;
    }

    @Override
    public LikeDto likeEntityToDto(LikeEntity likeEntity) {
        return LikeDto.builder()
                      .id(likeEntity.getId())
                      .post(this.postEntityToDto(likeEntity.getPost(),
                                                 false))
                      .user(this.userEntityToDto(likeEntity.getUser(),
                                                 false))
                      .build();
    }
}
