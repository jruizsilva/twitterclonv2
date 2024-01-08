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
import twitterclonv2.persistence.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

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
        if (isNotAdmin(userAuthenticated) && authorIsNotEqualToUserAuthenticated(post,
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
        if (isNotAdmin(userAuthenticated) && authorIsNotEqualToUserAuthenticated(post,
                                                                                 userAuthenticated)) {
            throw new RuntimeException("acceso denegado");
        }
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostEntity> findAllPostByUsername(String username) {
        return postRepository.findByUser_Username(username);
    }

    @Override
    public PostEntity findPostById(Long postId) {
        return postRepository.findById(postId)
                             .orElseThrow(() -> new CustomObjectNotFoundException("post not found"));

    }

    @Override
    public PostEntity addLikeToPost(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        List<PostEntity> postsLiked = userAuthenticated.getPostsLiked();
        List<UserEntity> likedByUsers = post.getLikedByUsers();
        if (!postsLiked.isEmpty() && !likedByUsers.isEmpty()) {
            Optional<UserEntity> likeOptional =
                    likedByUsers.stream()
                                .filter(user -> Objects.equals(user.getId(),
                                                               userAuthenticated.getId()))
                                .findFirst();
            if (likeOptional.isPresent()) {
                System.out.println("like already added");
                return post;
            }
        }
        postsLiked.add(post);
        userAuthenticated.setPostsLiked(postsLiked);
        userRepository.save(userAuthenticated);
        likedByUsers.add(userAuthenticated);
        post.setLikedByUsers(likedByUsers);
        return postRepository.save(post);
    }

    @Override
    public PostEntity removeLikeInPost(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        List<UserEntity> likedByUsers = post.getLikedByUsers();
        List<PostEntity> postsLiked = userAuthenticated.getPostsLiked();
        if (postsLiked.isEmpty() && likedByUsers.isEmpty()) {
            System.out.println("like to remove not found - like list is empty");
        }
        Optional<UserEntity> likeOptional =
                likedByUsers.stream()
                            .filter(user -> Objects.equals(user.getId(),
                                                           userAuthenticated.getId()))
                            .findFirst();
        if (likeOptional.isEmpty()) {
            System.out.println("like to remove not found");
            return post;
        }
        postsLiked.remove(post);
        userAuthenticated.setPostsLiked(postsLiked);
        userRepository.save(userAuthenticated);
        likedByUsers.remove(userAuthenticated);
        post.setLikedByUsers(likedByUsers);

        return postRepository.save(post);
    }

    private static boolean isNotAdmin(UserEntity userAuthenticated) {

        return userAuthenticated.getAuthorities()
                                .stream()
                                .noneMatch(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(),
                                                                              "ROLE_ADMINISTRATOR"));
    }

    private static boolean authorIsNotEqualToUserAuthenticated(PostEntity post,
                                                               UserEntity userAuthenticated) {
        return !Objects.equals(post.getUser()
                                   .getId(),
                               userAuthenticated.getId());
    }
}
