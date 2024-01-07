package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.LikeFacade;
import twitterclonv2.business.service.LikeService;
import twitterclonv2.domain.dto.like.request.LikeRequest;

@Component
@RequiredArgsConstructor
public class LikeFacadeImpl implements LikeFacade {
    private final LikeService likeService;

    @Override
    public void addLikeToPost(LikeRequest likeRequest) {
        likeService.addLikeToPost(likeRequest);
    }

    @Override
    public void removeLikeToPost(LikeRequest likeRequest) {
        likeService.removeLikeToPost(likeRequest);

    }
}
