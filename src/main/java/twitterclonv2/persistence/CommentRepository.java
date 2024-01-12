package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import twitterclonv2.domain.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}