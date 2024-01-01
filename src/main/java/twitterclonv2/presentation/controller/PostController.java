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
        return ResponseEntity.ok(postFacade.findByOrderByCreatedAtDesc());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId,
                                              @RequestBody @Valid
                                              PostRequest updatePostRequest) {
        return ResponseEntity.ok(postFacade.updatePost(updatePostRequest,
                                                       postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId) {
        postFacade.deletePostById(postId);
        return ResponseEntity.noContent()
                             .build();
    }

    @PatchMapping("/like")
    public ResponseEntity<PostDto> toggleUserLikeByPostId(@RequestParam(name = "postId") Long postId) {
        PostDto postDto = postFacade.toggleUserLikeByPostId(postId);
        return ResponseEntity.ok(postDto);
    }
}
