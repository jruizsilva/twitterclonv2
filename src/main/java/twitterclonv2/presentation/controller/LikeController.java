package twitterclonv2.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.domain.dto.post.PostDto;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final PostFacade postFacade;

    @GetMapping("/posts-liked-by-user")
    public ResponseEntity<List<PostDto>> getPostsLikedByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(postFacade.getPostsLikedByUser(userId));
    }
}
