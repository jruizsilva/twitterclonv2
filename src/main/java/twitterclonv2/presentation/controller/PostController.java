package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.service.PostService;
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

    @GetMapping("/username/{username}")
    public ResponseEntity<List<PostEntity>> findAllPostByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.findAllPostByUsername(username));
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
    public ResponseEntity<Void> addPostToPostsSaved(@PathVariable Long postId) {
        postService.addPostToPostsSaved(postId);
        return ResponseEntity.noContent()
                             .build();
    }

    @PatchMapping("/{postId}/removePostSaved")
    public ResponseEntity<Void> removePostFromPostsSaved(@PathVariable Long postId) {
        postService.removePostFromPostsSaved(postId);
        return ResponseEntity.noContent()
                             .build();
    }
}
