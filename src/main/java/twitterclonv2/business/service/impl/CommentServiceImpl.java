package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.CommentService;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.entity.CommentEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.CommentRepository;

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
        return null;
    }

    @Override
    public PostEntity likeComment(Long postId,
                                  Long commentId) {
        return null;
    }
}
