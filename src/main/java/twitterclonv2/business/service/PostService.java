package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface PostService {
    PostEntity createOnePost(PostRequest postRequest);
    List<PostEntity> findAll();
    List<PostEntity> findByOrderByCreatedAtDesc();
    PostEntity updatePost(PostRequest postRequest,
                          Long postId);
    void deletePostById(Long postId);
    PostEntity findPostById(Long postId);
    PostEntity savePost(PostEntity postEntity);
    PostEntity addLikeToPost(Long postId);
    PostEntity removeLikeInPost(Long postId);
}
