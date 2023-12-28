package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.business.mapper.PostMapper;
import twitterclonv2.business.service.PostService;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {
    private final PostService postService;
    private final PostMapper postMapper;

    @Override
    public PostEntity createOnePost(PostRequest postRequest) {
        return postService.createOnePost(postRequest);
    }

    @Override
    public List<PostDto> findAll() {
        List<PostEntity> postEntityList = postService.findAll();
        return postEntityList.stream()
                             .map(postMapper::toDto)
                             .toList();
    }
}
