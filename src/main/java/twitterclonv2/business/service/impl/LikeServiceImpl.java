package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.LikeService;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.persistence.LikeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public List<PostEntity> getPostsLikedByUser(Long userId) {
        List<LikeEntity> likes = likeRepository.findByUser_Id(userId);
        List<PostEntity> postsLikedByUser = new ArrayList<>();
        for (LikeEntity like : likes) {
            postsLikedByUser.add(like.getPost());
        }
        return postsLikedByUser;
    }
}
