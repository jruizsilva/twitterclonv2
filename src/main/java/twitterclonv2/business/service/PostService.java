package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface PostService {
    PostEntity createOnePost(PostRequest postRequest);
    PostEntity updatePost(PostRequest postRequest,
                          Long postId);
    void deletePostById(Long postId);
    PostEntity addLikeToPost(Long postId);
    PostEntity removeLikeInPost(Long postId);
    PostEntity findPostById(Long postId);
    PostEntity addPostToPostsSaved(Long postId);
    PostEntity removePostFromPostsSaved(Long postId);
    List<PostEntity> findAllPostsOrderByCreatedAtDesc();
    List<PostEntity> findAllPostsCreatedByUsername(String username);
    List<PostEntity> findAllPostsLikedByUsername(String username);
    List<PostEntity> findAllPostsSavedByUsername(String username);
    PostEntity save(PostEntity post);
}
