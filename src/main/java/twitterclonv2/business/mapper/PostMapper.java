package twitterclonv2.business.mapper;

import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.entity.PostEntity;

public interface PostMapper {
    PostDto toDto(PostEntity postEntity);
}
