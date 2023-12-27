package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

public interface UserFacade {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void registerUser(RegisterUserRequest registerUserRequest);
    UserEntity findUserAuthenticated();
}
