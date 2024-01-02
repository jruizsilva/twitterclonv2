package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity createOnePost(PostRequest postRequest) {
        UserEntity userEntity = userService.findUserAuthenticated();
        PostEntity postEntity = PostEntity.builder()
                                          .content(postRequest.getContent())
                                          .author(userEntity)
                                          .build();
        return postRepository.save(postEntity);
    }

    @Override
    public List<PostEntity> findByOrderByCreatedAtDesc() {
        return postRepository.findByOrderByCreatedAtDesc();
    }

    @Override
    public PostEntity updatePost(PostRequest postRequest,
                                 Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));
        if (post.getAuthor()
                .getId() != userAuthenticated.getId()) {
            throw new RuntimeException("post author is not equal to the user authenticated");
        }
        post.setContent(postRequest.getContent());
        return postRepository.save(post);
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostEntity addLikeToPost(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));
        LikeEntity likeToAdd = LikeEntity.builder()
                                         .user(userAuthenticated)
                                         .post(post)
                                         .build();
        List<LikeEntity> likes = post.getLikes();
        likes.add(likeToAdd);
        post.setLikes(likes);
        return postRepository.save(post);
    }

    @Override
    public PostEntity removeLikeInPost(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));
        List<LikeEntity> likes = post.getLikes();
        Optional<LikeEntity> likeToDeleteOptional = likes.stream()
                                                         .filter(like -> postId == like.getPost()
                                                                                       .getId() && like.getUser()
                                                                                                       .getId() == userAuthenticated.getId())
                                                         .findFirst();
        if (likeToDeleteOptional.isEmpty()) {
            return post;
        }
        LikeEntity likeToDelete = likeToDeleteOptional.get();
        likes.remove(likeToDelete);
        return null;
    }
}
