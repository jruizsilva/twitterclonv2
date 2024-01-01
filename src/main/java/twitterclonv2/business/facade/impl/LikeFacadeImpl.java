package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.LikeFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.service.LikeService;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.like.LikeDto;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeFacadeImpl implements LikeFacade {
    private final PostService postService;
    private final Mapper mapper;
    private final UserService userService;
    private final LikeService likeService;

    @Override
    public List<LikeDto> addLike(Long postId) {
        UserEntity userEntity = userService.findUserAuthenticated();
        PostEntity postEntity = postService.findPostById(postId);
        LikeEntity likeEntityToSave = LikeEntity.builder()
                                                .user(userEntity)
                                                .post(postEntity)
                                                .build();
        List<LikeEntity> likeEntityList = likeService.addLike(likeEntityToSave);
        return likeEntityList.stream()
                             .map(mapper::likeEntityToDto)
                             .toList();
    }

    @Override
    public List<LikeDto> removeLike(Long postId) {
        UserEntity userEntity = userService.findUserAuthenticated();
        PostEntity postEntity = postService.findPostById(postId);
        LikeEntity likeEntityToSave = LikeEntity.builder()
                                                .user(userEntity)
                                                .post(postEntity)
                                                .build();
        List<LikeEntity> likeEntityList = likeService.removeLike(likeEntityToSave);
        return likeEntityList.stream()
                             .map(mapper::likeEntityToDto)
                             .toList();
    }
}
