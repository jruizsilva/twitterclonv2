package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;

public interface UserFacade {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void registerUser(RegisterUserRequest registerUserRequest);
    UserDto findUserAuthenticated();
}
