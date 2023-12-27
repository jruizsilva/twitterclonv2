package twitterclonv2.business.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.business.service.impl.UserServiceImpl;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserServiceImpl userServiceImpl;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        return userServiceImpl.login(authenticationRequest);
    }

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        userServiceImpl.registerUser(registerUserRequest);
    }

    @Override
    public UserEntity findUserAuthenticated() {
        return userServiceImpl.findUserAuthenticated();
    }
}
