package twitterclonv2.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.facade.BookmarkFacade;
import twitterclonv2.domain.dto.bookmark.BookmarkDto;
import twitterclonv2.domain.dto.post.PostDto;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkFacade bookmarkFacade;

    @GetMapping
    public ResponseEntity<List<BookmarkDto>> findAllBookmarksByUsername(@RequestParam String username) {
        return ResponseEntity.ok(bookmarkFacade.findAllBookmarksByUsername(username));
    }

    @PostMapping
    public ResponseEntity<BookmarkDto> addBookmark(@RequestParam Long postId,
                                                   @RequestParam String username) {
        return ResponseEntity.ok(bookmarkFacade.addBookmark(postId,
                                                            username));
    }

    @DeleteMapping
    public ResponseEntity<BookmarkDto> removeBookmark(@RequestParam Long postId,
                                                      @RequestParam String username) {
        bookmarkFacade.removeBookmark(postId,
                                      username);
        return ResponseEntity.noContent()
                             .build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getBookmarkedPostsByUsername(@RequestParam String username) {
        return ResponseEntity.ok(bookmarkFacade.getBookmarkedPostsByUsername(username));
    }
}
