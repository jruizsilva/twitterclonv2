package twitterclonv2.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import twitterclonv2.common.util.Role;
import twitterclonv2.domain.entity.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        UserEntity userEntity1 = UserEntity.builder()
                                           .name("marcos")
                                           .username("nose")
                                           .password("marcos")
                                           .role(Role.USER)
                                           .description("primer usuario")
                                           .postsCreated(Collections.emptyList())
                                           .postsSaved(Collections.emptyList())
                                           .postsLiked(Collections.emptyList())
                                           .build();
        testEntityManager.persist(userEntity1);
        UserEntity userEntity2 = UserEntity.builder()
                                           .name("julio")
                                           .username("marcos")
                                           .password("marcos")
                                           .role(Role.USER)
                                           .description("segundo usuario")
                                           .postsCreated(Collections.emptyList())
                                           .postsSaved(Collections.emptyList())
                                           .postsLiked(Collections.emptyList())
                                           .build();
        testEntityManager.persist(userEntity2);
    }

    @Test
    void findByUsernameCaseFound() {
        Optional<UserEntity> userOptional = userRepository.findByUsername("jont");
        assertTrue(userOptional.isPresent());
        System.out.println(userOptional.get());
        assertEquals("jont",
                     userOptional.get()
                                 .getUsername());
    }

    @Test
    void findByUsernameCaseNotFound() {
        Optional<UserEntity> userOptional = userRepository.findByUsername("jontt");
        assertTrue(userOptional.isEmpty());
        assertEquals(Optional.empty(),
                     userOptional);
    }

    @Test
    void findByUsernameLikeOrNameLikeAllIgnoreCaseValid() {
        List<UserEntity> resultList = userRepository.findByUsernameLikeOrNameLikeAllIgnoreCase("%mar%");
        assertThat(resultList)
                .hasSize(2);
        assertTrue(resultList.stream()
                             .anyMatch(userEntity -> userEntity.getUsername()
                                                               .equals("nose")));
        assertTrue(resultList.stream()
                             .anyMatch(userEntity -> userEntity.getUsername()
                                                               .equals("marcos")));

    }
}