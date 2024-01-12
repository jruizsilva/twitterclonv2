package twitterclonv2.business.service;

import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.entity.CommentEntity;
import twitterclonv2.domain.entity.PostEntity;

public interface CommentService {
    PostEntity addCommentToPost(Long postId,
                                CommentRequest commentRequest);

    PostEntity removeComment(Long postId,
                             Long commentId);

    PostEntity likeComment(Long postId,
                           Long commentId);

    CommentEntity findCommentById(Long commentId);
}
