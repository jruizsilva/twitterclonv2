package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.PostRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity createOnePost(PostRequest postRequest) {
        UserEntity userEntity = userService.findUserAuthenticated();
        PostEntity postEntity = PostEntity.builder()
                                          .content(postRequest.getContent())
                                          .user(userEntity)
                                          .savedByUsers(Collections.emptyList())
                                          .likedByUsers(Collections.emptyList())
                                          .build();
        return postRepository.save(postEntity);
    }

    @Override
    public List<PostEntity> findAllPostsOrderByCreatedAtDesc() {
        return postRepository.findByOrderByCreatedAtDesc();
    }

    @Override
    public PostEntity updatePost(PostRequest postRequest,
                                 Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        if (!isAdmin(userAuthenticated) && !authorIsEqualToUserAuthenticated(post,
                                                                             userAuthenticated)) {
            throw new RuntimeException("acceso denegado");
        }
        post.setContent(postRequest.getContent());
        return postRepository.save(post);
    }

    @Override
    public void deletePostById(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        if (!isAdmin(userAuthenticated) && !authorIsEqualToUserAuthenticated(post,
                                                                             userAuthenticated)) {
            throw new RuntimeException("acceso denegado");
        }
        postRepository.deleteById(postId);
    }

    /*@Override
    public PostEntity addLikeToPost(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));
        List<LikeEntity> likes = post.getLikedByUsers();
        Optional<LikeEntity> likeOptional = likes.stream()
                                                 .filter(like -> Objects.equals(like.getUser()
                                                                                    .getId(),
                                                                                userAuthenticated.getId()))
                                                 .findFirst();
        if (likeOptional.isPresent()) {
            return post;
        }

        LikeEntity likeToAdd = LikeEntity.builder()
                                         .user(userAuthenticated)
                                         .post(post)
                                         .build();
        likes.add(likeToAdd);
        post.setLikedByUsers(likes);
        return postRepository.save(post);
    }

    @Override
    public PostEntity removeLikeInPost(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postRepository.findById(postId)
                                        .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));
        List<LikeEntity> likes = post.getLikedByUsers();
        Optional<LikeEntity> likeToDeleteOptional = likes.stream()
                                                         .filter(like -> postId.equals(like.getPost()
                                                                                           .getId()) && Objects.equals(like.getUser()
                                                                                                                           .getId(),
                                                                                                                       userAuthenticated.getId()))
                                                         .findFirst();
        if (likeToDeleteOptional.isEmpty()) {
            return post;
        }
        LikeEntity likeToDelete = likeToDeleteOptional.get();
        likes.remove(likeToDelete);
        post.setLikedByUsers(likes);

        return postRepository.save(post);
    }*/

    @Override
    public List<PostEntity> findAllPostOfCurrentUser() {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        return postRepository.findByUser_Username(userAuthenticated.getUsername());
    }

    @Override
    public PostEntity findPostById(Long postId) {
        return postRepository.findById(postId)
                             .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));

    }

    private static boolean isAdmin(UserEntity userAuthenticated) {

        return userAuthenticated.getAuthorities()
                                .stream()
                                .anyMatch(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(),
                                                                             "ROLE_ADMINISTRATOR"));
    }

    private static boolean authorIsEqualToUserAuthenticated(PostEntity post,
                                                            UserEntity userAuthenticated) {
        return Objects.equals(post.getUser()
                                  .getId(),
                              userAuthenticated.getId());
    }
}
