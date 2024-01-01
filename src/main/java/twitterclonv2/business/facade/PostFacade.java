package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;

import java.util.List;

public interface PostFacade {
    PostDto createOnePost(PostRequest postRequest);
    List<PostDto> findByOrderByCreatedAtDesc();
    PostDto updatePost(PostRequest postRequest,
                       Long postId);
    void deletePostById(Long postId);
}
