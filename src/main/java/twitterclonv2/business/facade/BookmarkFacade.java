package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.bookmark.BookmarkDto;
import twitterclonv2.domain.dto.post.PostDto;

import java.util.List;

public interface BookmarkFacade {
    List<BookmarkDto> findAllBookmarksByUsername(String username);
    BookmarkDto addBookmark(Long postId,
                        String username);
    void removeBookmark(Long bookmarkId,
                        String username);
    List<PostDto> getBookmarkedPostsByUsername(String username);
}
