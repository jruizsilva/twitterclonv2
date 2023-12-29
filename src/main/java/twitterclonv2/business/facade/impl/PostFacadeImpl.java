package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.service.PostService;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.entity.PostEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {
    private final PostService postService;
    private final Mapper mapper;

    @Override
    public PostDto createOnePost(PostRequest postRequest) {
        PostEntity postEntity = postService.createOnePost(postRequest);
        return mapper.postEntityToDto(postEntity,
                                      true);
    }

    @Override
    public List<PostDto> findAll() {
        List<PostEntity> postEntityList = postService.findAll();
        return postEntityList.stream()
                             .map(postEntity -> mapper.postEntityToDto(postEntity,
                                                                       true))
                             .toList();
    }

    @Override
    public List<PostDto> findByOrderByCreatedAtDesc() {
        List<PostEntity> postEntityList = postService.findByOrderByCreatedAtDesc();
        return postEntityList.stream()
                             .map(postEntity -> mapper.postEntityToDto(postEntity,
                                                                       true))
                             .toList();
    }
}
