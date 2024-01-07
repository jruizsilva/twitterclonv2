package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitterclonv2.business.service.LikeService;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.like.request.LikeRequest;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.LikeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public List<PostEntity> getPostsLikedByUser(Long userId) {
        List<LikeEntity> likes = likeRepository.findByUser_Id(userId);
        List<PostEntity> postsLikedByUser = new ArrayList<>();
        for (LikeEntity like : likes) {
            postsLikedByUser.add(like.getPost());
        }
        return postsLikedByUser;
    }

    @Override
    public void addLikeToPost(LikeRequest likeRequest) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        if (!Objects.equals(likeRequest.getUsername(),
                            userAuthenticated.getUsername())) {
            return;
        }
        PostEntity post = postService.findPostById(likeRequest.getPostId());
        List<LikeEntity> likes = post.getLikes();
        Optional<LikeEntity> likeOptional;
        if (!likes.isEmpty()) {
            likeOptional = likes.stream()
                                .filter(like -> Objects.equals(likeRequest.getPostId(),
                                                               like.getPost()
                                                                   .getId()) && Objects.equals(like.getUser()
                                                                                                   .getId(),
                                                                                               userAuthenticated.getId()))
                                .findFirst();
            if (likeOptional.isPresent()) {
                return;
            }
        }
        LikeEntity likeToAdd = LikeEntity.builder()
                                         .user(userAuthenticated)
                                         .post(post)
                                         .build();
        likeRepository.save(likeToAdd);
    }

    @Transactional
    @Override
    public void removeLikeToPost(LikeRequest likeRequest) {
        UserEntity userAuthenticated = userService.findUserAuthenticated();
        System.out.println(!Objects.equals(likeRequest.getUsername(),
                                           userAuthenticated.getUsername()));
        if (!Objects.equals(likeRequest.getUsername(),
                            userAuthenticated.getUsername())) {
            System.out.println("usernames not match");
            return;
        }
        PostEntity post = postService.findPostById(likeRequest.getPostId());
        List<LikeEntity> likes = post.getLikes();
        if (likes.isEmpty()) {
            System.out.println("likes is empty");
            return;
        }
        Optional<LikeEntity> likeToDeleteOptional =
                likes.stream()
                     .filter(like -> Objects.equals(likeRequest.getPostId(),
                                                    like.getPost()
                                                        .getId()) && Objects.equals(like.getUser()
                                                                                        .getId(),
                                                                                    userAuthenticated.getId()))
                     .findFirst();
        if (likeToDeleteOptional.isEmpty()) {
            System.out.println("like not found");
            return;
        }
        LikeEntity likeToDelete = likeToDeleteOptional.get();
        System.out.println("borrando " + likeToDelete.getId());
        System.out.println(likeToDelete);
        try {
            likeRepository.delete(likeToDelete);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al borrar like entity",
                                       e);
        }
    }
}
