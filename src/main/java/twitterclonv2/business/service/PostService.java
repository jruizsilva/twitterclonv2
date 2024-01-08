package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface PostService {
    PostEntity createOnePost(PostRequest postRequest);
    List<PostEntity> findAllPostsOrderByCreatedAtDesc();
    PostEntity updatePost(PostRequest postRequest,
                          Long postId);
    void deletePostById(Long postId);
    PostEntity addLikeToPost(Long postId);
    PostEntity removeLikeInPost(Long postId);
    List<PostEntity> findAllPostByUsername(String username);
    PostEntity findPostById(Long postId);
}
