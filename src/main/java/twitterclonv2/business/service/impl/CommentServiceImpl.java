package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.CommentService;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.entity.CommentEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.CommentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    @Override
    public PostEntity addCommentToPost(Long postId,
                                       CommentRequest commentRequest) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postService.findPostById(postId);
        CommentEntity commentToAdd = CommentEntity.builder()
                                                  .content(commentRequest.getContent())
                                                  .user(userAuthenticated)
                                                  .post(post)
                                                  .build();
        post.getComments()
            .add(commentToAdd);
        return postService.save(post);
    }

    @Override
    public PostEntity removeComment(Long postId,
                                    Long commentId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postService.findPostById(postId);
        List<CommentEntity> comments = post.getComments();
        if (comments.isEmpty()) {
            System.out.println("can't remove the comment list is empty");
            return post;
        }

        Optional<CommentEntity> commentOptional =
                comments.stream()
                        .filter(c -> Objects.equals(userAuthenticated.getId(),
                                                    c.getUser()
                                                     .getId()))
                        .findFirst();
        if (commentOptional.isEmpty()) {
            System.out.println("can't delete a comment that doesn't exist");
            return post;
        }
        comments.remove(commentOptional.get());
        post.setComments(comments);
        return postService.save(post);
    }

    @Override
    public PostEntity likeComment(Long postId,
                                  Long commentId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postService.findPostById(postId);
        CommentEntity comment = this.findCommentById(commentId);
        List<UserEntity> commentLikes = comment.getLikes();
        if (commentLikes.stream()
                        .anyMatch(c -> Objects.equals(c.getUsername(),
                                                      userAuthenticated.getUsername()))) {
            System.out.println("like already added");
            return post;
        }
        commentLikes.add(userAuthenticated);
        comment.setLikes(commentLikes);
        commentRepository.save(comment);

        return postService.save(post);
    }

    @Override
    public PostEntity removeLikeComment(Long postId,
                                        Long commentId) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        PostEntity post = postService.findPostById(postId);
        CommentEntity comment = this.findCommentById(commentId);
        List<UserEntity> commentLikes = comment.getLikes();
        if (commentLikes.stream()
                        .noneMatch(c -> Objects.equals(c.getUsername(),
                                                       userAuthenticated.getUsername()))) {
            System.out.println("like to remove not found");
            return post;
        }
        commentLikes.remove(userAuthenticated);
        comment.setLikes(commentLikes);
        commentRepository.save(comment);

        return postService.save(post);
    }

    @Override
    public List<CommentEntity> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public CommentEntity findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                                .orElseThrow(() -> new CustomObjectNotFoundException("comment not found"));
    }

}
