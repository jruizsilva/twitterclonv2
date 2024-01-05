package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.BookmarkFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.service.BookmarkService;
import twitterclonv2.domain.dto.bookmark.BookmarkDto;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.entity.BookmarkEntity;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkFacadeImpl implements BookmarkFacade {
    private final BookmarkService bookmarkService;
    private final Mapper mapper;

    @Override
    public List<BookmarkDto> findAllBookmarksByUsername(String username) {
        List<BookmarkEntity> bookmarks = bookmarkService.findAllBookmarks();
        return bookmarks.stream()
                        .map(mapper::bookmarkEntityToDto)
                        .toList();
    }

    @Override
    public PostDto addBookmark(Long postId,
                               String username) {
        PostEntity post = bookmarkService.addBookmark(postId,
                                                      username);
        return mapper.postEntityToDto(post);
    }

    @Override
    public void removeBookmark(Long bookmarkId,
                               String username) {
        bookmarkService.removeBookmark(bookmarkId,
                                       username);
    }

    @Override
    public List<PostDto> getBookmarkedPostsByUsername(String username) {
        List<PostEntity> posts = bookmarkService.getBookmarkedPostsByUsername(username);
        return posts.stream()
                    .map(mapper::postEntityToDto)
                    .toList();
    }
}
