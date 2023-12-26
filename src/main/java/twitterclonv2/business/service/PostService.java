package twitterclonv2.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AuthenticationService authenticationService;

    public PostEntity createOnePost(PostRequest postRequest) {
        UserEntity userEntity = authenticationService.findUserAuthenticated();
        PostEntity postEntity = PostEntity.builder()
                                          .content(postRequest.getContent())
                                          .author(userEntity)
                                          .build();
        return postRepository.save(postEntity);
    }
}
