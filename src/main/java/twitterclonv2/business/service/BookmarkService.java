package twitterclonv2.business.service;

import twitterclonv2.domain.entity.BookmarkEntity;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface BookmarkService {
    List<BookmarkEntity> findAllBookmarks();
    PostEntity addBookmark(Long postId,
                           String username);
    void removeBookmark(Long bookmarkId,
                        String username);
    List<PostEntity> getBookmarkedPostsByUsername(String username);
}