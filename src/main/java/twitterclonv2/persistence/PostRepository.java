package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("select p from PostEntity p order by p.createdAt DESC")
    List<PostEntity> findByOrderByCreatedAtDesc();
}