package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.BookmarkService;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.entity.BookmarkEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.BookmarkRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public List<BookmarkEntity> findAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @Override
    public BookmarkEntity addBookmark(Long postId,
                                      String username) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        if (!Objects.equals(username,
                            userAuthenticated.getUsername())) {
            throw new RuntimeException("usernames doesn't match");
        }
        Optional<BookmarkEntity> bookmarkOptional = userAuthenticated.getBookmarksSaved()
                                                                     .stream()
                                                                     .filter(bookmarkEntity -> bookmarkEntity.getPost()
                                                                                                             .getId() == postId)
                                                                     .findFirst();
        if (bookmarkOptional.isPresent()) {
            throw new RuntimeException("Bookmark already added");
        }
        PostEntity post = postService.findPostById(postId);
        BookmarkEntity bookmarkToAdd = BookmarkEntity.builder()
                                                     .post(post)
                                                     .user(userAuthenticated)
                                                     .build();
        return bookmarkRepository.save(bookmarkToAdd);
    }

    @Override
    public void removeBookmark(Long bookmarkId,
                               String username) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        if (!Objects.equals(username,
                            userAuthenticated.getUsername())) {
            throw new RuntimeException("usernames doesn't match");
        }
        bookmarkRepository.deleteById(bookmarkId);
    }

    @Override
    public List<PostEntity> getBookmarkedPostsByUsername(String username) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        if (!Objects.equals(username,
                            userAuthenticated.getUsername())) {
            throw new RuntimeException("usernames doesn't match");
        }
        List<BookmarkEntity> bookmarksSaved = userAuthenticated.getBookmarksSaved();
        List<PostEntity> posts = bookmarksSaved.stream()
                                               .map(BookmarkEntity::getPost)
                                               .toList();
        return posts;
    }
}
