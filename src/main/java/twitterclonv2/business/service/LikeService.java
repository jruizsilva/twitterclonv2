package twitterclonv2.business.service;

import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface LikeService {
    List<PostEntity> getPostsLikedByUser(Long userId);
}
