package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.mapper.PostMapper;
import twitterclonv2.business.service.LikeService;
import twitterclonv2.business.service.PostService;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {
    private final PostService postService;
    private final LikeService likeService;
    private final PostMapper postMapper;
    private final Mapper mapper;

    @Override
    public PostDto createOnePost(PostRequest postRequest) {
        PostEntity postEntity = postService.createOnePost(postRequest);
        System.out.println(postEntity);
        return mapper.postEntityToDto(postEntity
        );
    }

    @Override
    public List<PostDto> findByOrderByCreatedAtDesc() {
        List<PostEntity> postEntityList = postService.findByOrderByCreatedAtDesc();
        return postEntityList.stream()
                             .map(postMapper::postEntityToDtoWithoutChildren)
                             .toList();
        /*return postEntityList.stream()
                             .map(postEntity -> mapper.postEntityToDto(postEntity,
                                                                       true))
                             .toList();*/
    }

    @Override
    public PostDto updatePost(PostRequest postRequest,
                              Long postId) {

        PostEntity postEntityUpdated = postService.updatePost(postRequest,
                                                              postId);
        return mapper.postEntityToDto(postEntityUpdated);
    }

    @Override
    public void deletePostById(Long postId) {
        postService.deletePostById(postId);
    }

    @Override
    public PostDto addLikeToPost(Long postId) {
        PostEntity postLiked = postService.addLikeToPost(postId);
        return mapper.postEntityToDto(postLiked);
    }

    @Override
    public PostDto removeLikeInPost(Long postId) {
        PostEntity postLikeRemoved = postService.removeLikeInPost(postId);
        return mapper.postEntityToDto(postLikeRemoved);
    }

    @Override
    public List<PostDto> findAllPostOfCurrentUser() {
        List<PostEntity> postEntityList = postService.findAllPostOfCurrentUser();
        return postEntityList.stream()
                             .map(postEntity -> mapper.postEntityToDto(postEntity))
                             .toList();
    }

    @Override
    public List<PostDto> getPostsLikedByUser(Long userId) {
        List<PostEntity> postsLikedByUser = likeService.getPostsLikedByUser(userId);
        return postsLikedByUser.stream()
                               .map(postEntity -> mapper.postEntityToDto(postEntity))
                               .toList();
    }
}
