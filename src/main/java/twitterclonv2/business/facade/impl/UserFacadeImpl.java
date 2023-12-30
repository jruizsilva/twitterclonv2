package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.business.mapper.Mapper;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final Mapper mapper;

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
}
