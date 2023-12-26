package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitterclonv2.business.service.PostService;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostEntity> createOnePost(@RequestBody @Valid PostRequest postRequest) {
        PostEntity postEntitySaved = postService.createOnePost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(postEntitySaved);
    }
}
