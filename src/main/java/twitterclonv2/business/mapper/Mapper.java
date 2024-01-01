package twitterclonv2.business.mapper;

import twitterclonv2.domain.dto.like.LikeDto;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

public interface Mapper {
    PostDto postEntityToDto(PostEntity postEntity,
                            boolean includeAuthor);
    UserDto userEntityToDto(UserEntity userEntity,
                            boolean includePosts);
    LikeDto likeEntityToDto(LikeEntity likeEntity);
}
