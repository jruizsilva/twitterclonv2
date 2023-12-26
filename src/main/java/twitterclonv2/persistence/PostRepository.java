package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import twitterclonv2.domain.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}