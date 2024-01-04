package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostFacade postFacade;

    @PostMapping
    public ResponseEntity<PostDto> createOnePost(@RequestBody @Valid PostRequest postRequest) {
        PostDto postDto = postFacade.createOnePost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(postDto);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll() {
        return ResponseEntity.ok(postFacade.findAllPostsOrderByCreatedAtDesc());
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId,
                                              @RequestBody @Valid
                                              PostRequest updatePostRequest) {
        return ResponseEntity.ok(postFacade.updatePost(updatePostRequest,
                                                       postId));
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<PostDto> likePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postFacade.addLikeToPost(postId));
    }

    @PatchMapping("/{postId}/removeLike")
    public ResponseEntity<PostDto> removeLikeInPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postFacade.removeLikeInPost(postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId) {
        postFacade.deletePostById(postId);
        return ResponseEntity.noContent()
                             .build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> findAllPostOfCurrentUser() {
        return ResponseEntity.ok(postFacade.findAllPostOfCurrentUser());
    }
}
