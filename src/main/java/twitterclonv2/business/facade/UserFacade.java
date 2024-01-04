package twitterclonv2.business.facade;

import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;

import java.util.List;

public interface UserFacade {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse registerUser(RegisterUserRequest registerUserRequest);
    UserDto findUserAuthenticated();
    UserDto updateUser(UpdateUserRequest updateUserRequest);
    List<UserDto> findAllUsers();
    List<UserDto> searchUsersByUsernameOrName(String peopleToSearch);
}
