package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.CommentEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.PostRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        PostEntity postToDelete = this.findPostById(postId);
        if (isNotAdmin(userAuthenticated) && authorIsNotEqualToUserAuthenticated(postToDelete,
                                                                                 userAuthenticated)) {
            throw new RuntimeException("acceso denegado");
        }
        // Desasociar el post de los usuarios antes de eliminarlo
        postToDelete.getSavedByUsers()
                    .forEach(user -> user.getPostsSaved()
                                         .remove(postToDelete));
        postToDelete.getLikedByUsers()
                    .forEach(user -> user.getPostsLiked()
                                         .remove(postToDelete));

        // Limpiar las colecciones asociadas al post
        postToDelete.getSavedByUsers()
                    .clear();
        postToDelete.getLikedByUsers()
                    .clear();

        postRepository.delete(postToDelete);
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
        List<UserEntity> likedByUsers = post.getLikedByUsers();
        if (userAlreadyLikedPost(userAuthenticated,
                                 likedByUsers)) {
            System.out.println("like already added");
            return post;
        }
        return updatePostAndUserEntities(userAuthenticated,
                                         post);
    }

    private boolean userAlreadyLikedPost(UserEntity user,
                                         List<UserEntity> likedByUsers) {
        return likedByUsers.stream()
                           .anyMatch(likedUser -> Objects.equals(likedUser.getId(),
                                                                 user.getId()));
    }

    private PostEntity updatePostAndUserEntities(UserEntity user,
                                                 PostEntity post) {
        List<PostEntity> postsLiked = user.getPostsLiked();
        postsLiked.add(post);
        user.setPostsLiked(postsLiked);

        List<UserEntity> likedByUsers = post.getLikedByUsers();
        likedByUsers.add(user);
        post.setLikedByUsers(likedByUsers);

        userService.save(user);
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
            return post;
        }
        postsLiked.removeIf(postEntity -> Objects.equals(postEntity.getId(),
                                                         post.getId()));
        likedByUsers.removeIf(userEntity -> Objects.equals(userEntity.getId(),
                                                           userAuthenticated.getId()));

        userAuthenticated.setPostsLiked(postsLiked);
        userService.save(userAuthenticated);

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

    @Override
    public PostEntity addPostToPostsSaved(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        List<PostEntity> postsSaved = userAuthenticated.getPostsSaved();
        Optional<PostEntity> postOptional = Optional.empty();
        if (!postsSaved.isEmpty()) {
            postOptional = postsSaved.stream()
                                     .filter(p -> Objects.equals(p.getId(),
                                                                 post.getId()))
                                     .findFirst();
        }
        if (postOptional.isPresent()) {
            System.out.println("post already added in postsSaved");
            return post;
        }
        postsSaved.add(post);
        userAuthenticated.setPostsSaved(postsSaved);
        userService.save(userAuthenticated);

        List<UserEntity> savedByUsers = post.getSavedByUsers();
        savedByUsers.add(userAuthenticated);
        post.setSavedByUsers(savedByUsers);
        return postRepository.save(post);
    }

    @Override
    public PostEntity removePostFromPostsSaved(Long postId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);

        List<UserEntity> savedByUsers = post.getSavedByUsers();
        List<PostEntity> postsSaved = userAuthenticated.getPostsSaved();

        if (postsSaved.isEmpty() && savedByUsers.isEmpty()) {
            System.out.println("post to remove not found - both list are empty");
            return post;
        }

        postsSaved.removeIf(p -> Objects.equals(p.getId(),
                                                post.getId()));
        savedByUsers.removeIf(u -> Objects.equals(u.getId(),
                                                  userAuthenticated.getId()));

        userAuthenticated.setPostsSaved(postsSaved);
        userService.save(userAuthenticated);

        post.setSavedByUsers(savedByUsers);
        return postRepository.save(post);
    }

    @Override
    public List<PostEntity> findAllPostsCreatedByUsername(String username) {
        UserEntity user = userService.findUserByUsername(username);
        return user.getPostsCreated();
    }

    @Override
    public List<PostEntity> findAllPostsLikedByUsername(String username) {
        UserEntity user = userService.findUserByUsername(username);
        return user.getPostsLiked();
    }

    @Override
    public List<PostEntity> findAllPostsSavedByUsername(String username) {
        UserEntity user = userService.findUserByUsername(username);
        return user.getPostsSaved();
    }

    @Override
    public PostEntity addCommentToPost(Long postId,
                                       CommentRequest commentRequest) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        CommentEntity commentToAdd = CommentEntity.builder()
                                                  .content(commentRequest.getContent())
                                                  .user(userAuthenticated)
                                                  .build();
        post.getComments()
            .add(commentToAdd);
        commentToAdd.setPost(post);
        return postRepository.save(post);
    }

    @Override
    public PostEntity removeComment(Long postId,
                                    Long commentId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        Optional<CommentEntity> commentToDelete =
                post.getComments()
                    .stream()
                    .filter(commentEntity -> Objects.equals(commentEntity.getUser()
                                                                         .getUsername(),
                                                            userAuthenticated.getUsername()) && Objects.equals(commentId,
                                                                                                               commentEntity.getId()))
                    .findFirst();
        if (commentToDelete.isEmpty()) {
            System.out.println("comment to delete not found");
            return post;
        }
        post.getComments()
            .remove(commentToDelete.get());
        return postRepository.save(post);
    }

    @Override
    public PostEntity likeComment(Long postId,
                                  Long commentId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = this.findPostById(postId);
        Optional<CommentEntity> commentToLike =
                post.getComments()
                    .stream()
                    .filter(commentEntity -> Objects.equals(commentEntity.getUser()
                                                                         .getUsername(),
                                                            userAuthenticated.getUsername()) && Objects.equals(commentId,
                                                                                                               commentEntity.getId()))
                    .findFirst();
        if (commentToLike.isEmpty()) {
            System.out.println("comment to like not found");
            return post;
        }
        List<UserEntity> likes = commentToLike.get()
                                              .getLikes();
        if (likes.stream()
                 .anyMatch(userEntity -> Objects.equals(userEntity.getUsername(),
                                                        userAuthenticated.getUsername()))) {
            System.out.println("like already added");
            return post;
        }
        likes.add(userAuthenticated);
        post.setLikedByUsers(likes);

        return postRepository.save(post);
    }
}
