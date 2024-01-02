package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import twitterclonv2.domain.entity.LikeEntity;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    @Query("select l from LikeEntity l where l.user.id = :id")
    List<LikeEntity> findByUser_Id(@Param("id") Long id);
}