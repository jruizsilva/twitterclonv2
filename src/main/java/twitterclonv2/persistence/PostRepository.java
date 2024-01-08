package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("select p from PostEntity p order by p.createdAt DESC")
    List<PostEntity> findByOrderByCreatedAtDesc();
    @Query("select p from PostEntity p where p.user.username = :username")
    List<PostEntity> findByUser_Username(@Param("username") String username);
}