package twitterclonv2.business.service;

import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    AuthenticationResponse register(RegisterUserRequest registerUserRequest);
    UserEntity findUserAuthenticated();
    UserEntity updateUser(String username,
                          UpdateUserRequest updateUserRequest);
    List<UserEntity> findAllUsers();
    List<UserEntity> searchUsersByUsernameOrName(String peopleToSearch);
    UserEntity findUserById(Long userId);
    UserEntity findUserByUsername(String username);
    UserEntity save(UserEntity userEntity);
    void deleteUserByUsername(String username);
    UserEntity addFollower(String username);
    UserEntity removeFollower(String username);
    List<UserEntity> findAllFollowersByUsername(String username);

    List<UserEntity> findAllUsersFollowingByUsername(String username);
}
