package twitterclonv2.business.service;

import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    void registerUser(RegisterUserRequest registerUserRequest);
    UserEntity findUserAuthenticated();
    UserEntity updateUser(UserEntity userEntityRequest);
    List<UserEntity> findAllUsers();
    List<UserEntity> searchUsersByUsernameOrName(String peopleToSearch);
}
