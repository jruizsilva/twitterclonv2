package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.LikeService;
import twitterclonv2.domain.entity.LikeEntity;
import twitterclonv2.persistence.LikeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    @Override
    public List<LikeEntity> addLike(LikeEntity likeEntity) {
        likeRepository.save(likeEntity);
        return likeRepository.findAll();
    }

    @Override
    public List<LikeEntity> removeLike(LikeEntity likeEntity) {
        likeRepository.delete(likeEntity);
        return likeRepository.findAll();
    }

    @Override
    public LikeEntity saveLike(LikeEntity likeEntity) {
        return likeRepository.save(likeEntity);
    }
}
