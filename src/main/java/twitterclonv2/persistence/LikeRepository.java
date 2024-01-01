package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import twitterclonv2.domain.entity.LikeEntity;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    @Query("select l from LikeEntity l where upper(l.user.username) = upper(:username)")
    List<LikeEntity> findByUser_UsernameAllIgnoreCase(@Param("username") String username);
}