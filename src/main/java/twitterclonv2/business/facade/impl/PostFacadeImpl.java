package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.PostFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.domain.dto.post.PostDto;
import twitterclonv2.domain.dto.post.request.PostRequest;
import twitterclonv2.domain.dto.post.request.UpdatePostRequest;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostFacadeImpl implements PostFacade {
    private final PostService postService;
    private final Mapper mapper;
    private final UserService userService;

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

    @Override
    public PostDto updatePost(UpdatePostRequest updatePostRequest) {
        UserEntity userEntity = userService.findUserAuthenticated();
        Optional<PostEntity> postEntityOptional = userEntity.getPosts()
                                                            .stream()
                                                            .map(post -> Objects.equals(post.getId(),
                                                                                        updatePostRequest.getId()) ? post : null)
                                                            .filter(Objects::nonNull)
                                                            .findFirst();

        if (postEntityOptional.isEmpty()) {
            throw new CustomObjectNotFoundException("Post not found in current user auth");
        }
        PostEntity postEntityToUpdate = postEntityOptional.get();
        postEntityToUpdate.setContent(updatePostRequest.getContent());
        PostEntity postEntityUpdated = postService.updatePost(postEntityToUpdate);

        return mapper.postEntityToDto(postEntityUpdated,
                                      true);
    }

    @Override
    public void deletePostById(Long postId) {
        postService.deletePostById(postId);
    }
}
