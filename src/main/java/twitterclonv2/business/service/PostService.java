package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface PostService {
    PostEntity createOnePost(PostRequest postRequest);
    List<PostEntity> findAll();
    List<PostEntity> findByOrderByCreatedAtDesc();
    PostEntity updatePost(PostEntity updatePostEntityRequest);
    void deletePostById(Long postId);
}
