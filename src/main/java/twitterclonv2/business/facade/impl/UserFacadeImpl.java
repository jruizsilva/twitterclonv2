package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.service.PostService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.PostEntity;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final Mapper mapper;
    private final PostService postService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return userService.login(authenticationRequest);
    }

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        userService.registerUser(registerUserRequest);
    }

    @Override
    public UserDto findUserAuthenticated() {
        UserEntity userEntity = userService.findUserAuthenticated();
        return mapper.userEntityToDto(userEntity,
                                      true);
    }

    @Override
    public UserDto updateUser(UpdateUserRequest updateUserRequest) {
        UserEntity userEntity = userService.findUserAuthenticated();
        if (updateUserRequest.getName() != null && !updateUserRequest.getName()
                                                                     .isBlank()) {
            userEntity.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getDescription() != null && !updateUserRequest.getDescription()
                                                                            .isBlank()) {
            userEntity.setDescription(updateUserRequest.getDescription());
        }
        UserEntity userEntityUpdated = userService.updateUser(userEntity);
        return mapper.userEntityToDto(userEntityUpdated,
                                      true);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntityList = userService.findAllUsers();
        return userEntityList.stream()
                             .map(userEntity -> mapper.userEntityToDto(userEntity,
                                                                       true))
                             .toList();
    }

    @Override
    public List<UserDto> searchUsersByUsernameOrName(String peopleToSearch) {
        List<UserEntity> userEntityList = userService.searchUsersByUsernameOrName(peopleToSearch);
        System.out.println(userEntityList.toString());

        return userEntityList.stream()
                             .map(userEntity -> mapper
                                     .userEntityToDto(userEntity,
                                                      true))
                             .toList();
    }

    @Override
    public UserEntity toggleUserLikeByPostId(Long postId) {
        UserEntity userEntity = userService.findUserAuthenticated();
        PostEntity postLiked = postService.findPostById(postId);
        List<PostEntity> postsUserLikedList = userEntity.getPostsLiked();
        boolean liked = postsUserLikedList.contains(postLiked);

        if (liked) {
            postsUserLikedList.remove(postLiked);
        } else {
            postsUserLikedList.add(postLiked);
        }
        userEntity.setPostsLiked(postsUserLikedList);
        return userService.saveUser(userEntity);
    }
}
