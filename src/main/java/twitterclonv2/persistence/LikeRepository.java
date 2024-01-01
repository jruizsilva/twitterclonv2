package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import twitterclonv2.domain.entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
}