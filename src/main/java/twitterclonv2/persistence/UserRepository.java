package twitterclonv2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);
    @Query("select u from UserEntity u where upper(u.username) like upper(?1) or upper(u.name) like upper(?1)")
    List<UserEntity> findByUsernameLikeOrNameLikeAllIgnoreCase(String peopleToSearch);

}