package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.like.LikeDto;

import java.util.List;

public interface LikeFacade {
    List<LikeDto> addLike(Long postId);
    List<LikeDto> removeLike(Long postId);
}
