package twitterclonv2.business.service;

import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    AuthenticationResponse registerUser(RegisterUserRequest registerUserRequest);
    UserEntity findUserAuthenticated();
    UserEntity updateUser(UpdateUserRequest updateUserRequest);
    List<UserEntity> findAllUsers();
    List<UserEntity> searchUsersByUsernameOrName(String peopleToSearch);
    UserEntity findUserById(Long userId);
    UserEntity saveUser(UserEntity userEntity);
}
