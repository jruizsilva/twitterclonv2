package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;

import java.util.List;

public interface PostFacade {
    PostDto createOnePost(PostRequest postRequest);
    List<PostDto> findAllPostsOrderByCreatedAtDesc();
    PostDto updatePost(PostRequest postRequest,
                       Long postId);
    void deletePostById(Long postId);
    PostDto addLikeToPost(Long postId);
    PostDto removeLikeInPost(Long postId);
    List<PostDto> findAllPostOfCurrentUser();
    List<PostDto> getPostsLikedByUser(Long userId);
}
