package twitterclonv2.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        UserEntity user = UserEntity.builder()
                                    .name("usuario creado desde tests")
                                    .username("usertest")
                                    .password("usertest")
                                    .build();
        testEntityManager.persist(user);

        PostEntity post = PostEntity.builder()
                                    .content("Posts creado desde tests por usertest")
                                    .user(user)
                                    .build();
        PostEntity post2 = PostEntity.builder()
                                     .content("Posts creado desde tests por usertest")
                                     .user(user)
                                     .build();

        testEntityManager.persist(post);
        testEntityManager.persist(post2);
    }

    @Test
    void findPostsByOrderByCreatedAtDescCaseValid() {
        List<PostEntity> result = postRepository.findByOrderByCreatedAtDesc();

        Assertions.assertEquals(8,
                                result.size());
        Assertions.assertTrue(result.get(0)
                                    .getCreatedAt()
                                    .isAfter(result.get(1)
                                                   .getCreatedAt()));
        Assertions.assertTrue(result.get(1)
                                    .getCreatedAt()
                                    .isAfter(result.get(2)
                                                   .getCreatedAt()));
    }

    @Test
    void findPostsByUser_UsernameCaseValid() {
        List<PostEntity> result = postRepository.findByUser_Username("usertest");
        System.out.println(result);
        Assertions.assertEquals(2,
                                result.size());
    }
}