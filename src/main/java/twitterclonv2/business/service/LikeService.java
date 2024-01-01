package twitterclonv2.business.service;

import twitterclonv2.domain.entity.LikeEntity;

import java.util.List;

public interface LikeService {
    List<LikeEntity> addLike(LikeEntity likeEntity);
    List<LikeEntity> removeLike(LikeEntity likeEntity);
    LikeEntity saveLike(LikeEntity likeEntity);
}
