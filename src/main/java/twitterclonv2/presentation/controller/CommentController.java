package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.service.CommentService;
import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.entity.CommentEntity;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentEntity>> findAllComments() {
        return ResponseEntity.ok(commentService.findAllComments());
    }

    @PostMapping("/posts/{postId}/addComment")
    public ResponseEntity<PostEntity> addCommentToPost(@PathVariable Long postId,
                                                       @RequestBody @Valid CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.addCommentToPost(postId,
                                                                 commentRequest));
    }

    @DeleteMapping("/{commentId}/posts/{postId}/removeComment")
    public ResponseEntity<PostEntity> removeComment(@PathVariable Long postId,
                                                    @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.removeComment(postId,
                                                              commentId));
    }

    @PatchMapping("/{commentId}/posts/{postId}/likeComment")
    public ResponseEntity<PostEntity> likeComment(@PathVariable Long postId,
                                                  @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.likeComment(postId,
                                                            commentId));
    }

    @PatchMapping("/{commentId}/posts/{postId}/removeLikeComment")
    public ResponseEntity<PostEntity> removeLikeComment(@PathVariable Long postId,
                                                        @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.removeLikeComment(postId,
                                                                  commentId));
    }

    @PatchMapping("/{commentId}/posts/{postId}/editComment")
    public ResponseEntity<PostEntity> editComment(@PathVariable Long postId,
                                                  @PathVariable Long commentId,
                                                  @RequestBody @Valid CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.editComment(postId,
                                                            commentId,
                                                            commentRequest));
    }
}
