package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.like.request.LikeRequest;

public interface LikeFacade {
    void addLikeToPost(LikeRequest likeRequest);
    void removeLikeToPost(LikeRequest likeRequest);
}
