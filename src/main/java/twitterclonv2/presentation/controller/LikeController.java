package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.facade.LikeFacade;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.domain.dto.like.request.LikeRequest;
import twitterclonv2.domain.dto.post.PostDto;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final PostFacade postFacade;
    private final LikeFacade likeFacade;

    @PostMapping
    public ResponseEntity<Void> addLikeToPost(@RequestBody @Valid LikeRequest likeRequest) {
        likeFacade.addLikeToPost(likeRequest);
        return ResponseEntity.noContent()
                             .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLikeToPost(@RequestBody @Valid LikeRequest likeRequest) {
        likeFacade.removeLikeToPost(likeRequest);
        return ResponseEntity.noContent()
                             .build();
    }

    @GetMapping("/posts-liked-by-user")
    public ResponseEntity<List<PostDto>> getPostsLikedByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(postFacade.getPostsLikedByUser(userId));
    }
}
