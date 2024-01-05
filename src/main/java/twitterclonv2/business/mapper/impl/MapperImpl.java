package twitterclonv2.business.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.mapper.*;
import twitterclonv2.domain.dto.bookmark.BookmarkDto;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.BookmarkEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class MapperImpl implements Mapper {
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final LikeMapper likeMapper;
    private final BookmarkMapper bookmarkMapper;

    @Override
    public PostDto postEntityToDto(PostEntity postEntity) {
        PostDto postDto = postMapper.postEntityToDtoWithoutChildren(postEntity);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM 'at' HH:mm");
        String formattedDate = postEntity.getCreatedAt()
                                         .format(formatter);
        postDto.setCreatedAt(formattedDate);
        postDto.setAuthor(userMapper.userEntityToDtoWithoutChildren(postEntity.getAuthor()));
        postDto.setLikes(likeMapper.likeEntityListToDtoListWithoutChildren(postEntity.getLikes()));
        return postDto;
    }

    @Override
    public UserDto userEntityToDto(UserEntity userEntity) {
        UserDto userDto = userMapper.userEntityToDtoWithoutChildren(userEntity);
        userDto.setPostsCreated(postMapper.postEntityListToDtoListWithoutChildren(userEntity.getPostsCreated()));
        userDto.setPostsLiked(likeMapper.likeEntityListToDtoListWithoutChildren(userEntity.getLikes()));
        userDto.setBookmarksSaved(bookmarkMapper.bookmarkEntityListToDtoListWithoutChildren(userEntity.getBookmarksSaved()));
        return userDto;
    }

    @Override
    public BookmarkDto bookmarkEntityToDto(BookmarkEntity bookmarkEntity) {
        BookmarkDto.BookmarkDtoBuilder bookmarkDto = BookmarkDto.builder();
        bookmarkDto.id(bookmarkEntity.getId());
        bookmarkDto.post(postMapper.postEntityToDtoWithoutChildren(bookmarkEntity.getPost()));
        System.out.println("mapper 1");
        bookmarkDto.user(userMapper.userEntityToDtoWithoutChildren(bookmarkEntity.getUser()));
        System.out.println("mapper 2");
        System.out.println(bookmarkDto);
        return bookmarkDto.build();
    }
}
