package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.entity.CommentEntity;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface CommentService {
    List<CommentEntity> findAllComments();

    CommentEntity findCommentById(Long commentId);

    PostEntity addCommentToPost(Long postId,
                                CommentRequest commentRequest);

    PostEntity removeComment(Long postId,
                             Long commentId);

    PostEntity likeComment(Long postId,
                           Long commentId);

    PostEntity removeLikeComment(Long postId,
                                 Long commentId);
}
