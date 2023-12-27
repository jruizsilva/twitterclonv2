package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.business.service.PostService;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

@Component
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {
    private final PostService postService;

    @Override
    public PostEntity createOnePost(PostRequest postRequest) {
        return postService.createOnePost(postRequest);
    }
}
