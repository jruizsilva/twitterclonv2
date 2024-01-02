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

import java.util.List;

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
        UserEntity userEntityUpdated = userService.updateUser(updateUserRequest);
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

        return userEntityList.stream()
                             .map(userEntity -> mapper
                                     .userEntityToDto(userEntity,
                                                      true))
                             .toList();
    }
}
