package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.service.PostService;
import twitterclonv2.domain.dto.post.request.CommentRequest;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostEntity> createOnePost(@RequestBody @Valid PostRequest postRequest) {
        PostEntity postEntity = postService.createOnePost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(postEntity);
    }

    @GetMapping
    public ResponseEntity<List<PostEntity>> findAll() {
        return ResponseEntity.ok(postService.findAllPostsOrderByCreatedAtDesc());
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostEntity> updatePost(@PathVariable Long postId,
                                                 @RequestBody @Valid
                                                 PostRequest updatePostRequest) {
        return ResponseEntity.ok(postService.updatePost(updatePostRequest,
                                                        postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent()
                             .build();
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<PostEntity> likePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.addLikeToPost(postId));
    }

    @PatchMapping("/{postId}/removeLike")
    public ResponseEntity<PostEntity> removeLikeInPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.removeLikeInPost(postId));
    }

    @PatchMapping("/{postId}/savePost")
    public ResponseEntity<PostEntity> addPostToPostsSaved(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.addPostToPostsSaved(postId));
    }

    @PatchMapping("/{postId}/removePostSaved")
    public ResponseEntity<PostEntity> removePostFromPostsSaved(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.removePostFromPostsSaved(postId));
    }

    @GetMapping("/username/{username}/postsCreated")
    public ResponseEntity<List<PostEntity>> findAllPostsCreatedByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.findAllPostsCreatedByUsername(username));
    }

    @GetMapping("/username/{username}/postsLiked")
    public ResponseEntity<List<PostEntity>> findAllPostsLikedByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.findAllPostsLikedByUsername(username));
    }

    @GetMapping("/username/{username}/postsSaved")
    public ResponseEntity<List<PostEntity>> findAllPostsSavedByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.findAllPostsSavedByUsername(username));
    }

    @PatchMapping("/{postId}/addComment")
    public ResponseEntity<PostEntity> addCommentToPost(@PathVariable Long postId,
                                                       @RequestBody @Valid
                                                       CommentRequest commentRequest) {
        return ResponseEntity.ok(postService.addCommentToPost(postId,
                                                              commentRequest));

    }

    @PatchMapping("/{postId}/removeComment")
    public ResponseEntity<PostEntity> removeCommentToPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.removeComment(postId));
    }

    @PatchMapping("/{postId}/likeComment")
    public ResponseEntity<PostEntity> likeComment(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.likeComment(postId));
    }
}