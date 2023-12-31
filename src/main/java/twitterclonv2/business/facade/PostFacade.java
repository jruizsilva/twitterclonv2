package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.dto.post.request.UpdatePostRequest;

import java.util.List;

public interface PostFacade {
    PostDto createOnePost(PostRequest postRequest);
    List<PostDto> findAll();
    List<PostDto> findByOrderByCreatedAtDesc();
    PostDto updatePost(UpdatePostRequest updatePostRequest);
}
