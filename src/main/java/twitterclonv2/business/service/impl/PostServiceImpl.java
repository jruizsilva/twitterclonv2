package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity createOnePost(PostRequest postRequest) {
        UserEntity userEntity = userService.findUserAuthenticated();
        PostEntity postEntity = PostEntity.builder()
                                          .content(postRequest.getContent())
                                          .author(userEntity)
                                          .build();
        return postRepository.save(postEntity);
    }

    @Override
    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<PostEntity> findByOrderByCreatedAtDesc() {
        return postRepository.findByOrderByCreatedAtDesc();
    }

    @Override
    public PostEntity updatePost(PostEntity updatePostEntityRequest) {
        return postRepository.save(updatePostEntityRequest);
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostEntity findPostById(Long postId) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        if (postEntityOptional.isEmpty()) {
            throw new CustomObjectNotFoundException("post with " + postId + " not found");
        }
        return postEntityOptional.get();
    }

    @Override
    public PostEntity savePost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }
}
