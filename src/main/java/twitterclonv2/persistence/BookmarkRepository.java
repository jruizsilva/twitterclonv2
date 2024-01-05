package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import twitterclonv2.domain.entity.BookmarkEntity;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
}