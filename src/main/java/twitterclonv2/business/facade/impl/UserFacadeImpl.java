package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.business.mapper.UserMapper;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userServiceImpl;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return userServiceImpl.login(authenticationRequest);
    }

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        userServiceImpl.registerUser(registerUserRequest);
    }

    @Override
    public UserDto findUserAuthenticated() {
        UserEntity userEntity = userServiceImpl.findUserAuthenticated();
        return userMapper.toDto(userEntity);
    }
}
