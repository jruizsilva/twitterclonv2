package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.JwtService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.common.util.Role;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                        authenticationRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);

        UserEntity userEntity = userRepository.findByUsername(authenticationRequest.getUsername())
                                              .orElseThrow(() -> new CustomObjectNotFoundException("Incorrect username or password. - User not found - 1"));

        String jwt = jwtService.generateToken(userEntity);
        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse register(RegisterUserRequest registerUserRequest) {
        UserEntity userEntity =
                UserEntity.builder()
                          .name(registerUserRequest.getName())
                          .username(registerUserRequest.getUsername())
                          .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                          .role(Role.USER)
                          .postsCreated(Collections.emptyList())
                          .postsSaved(Collections.emptyList())
                          .postsLiked(Collections.emptyList())
                          .build();
        userRepository.save(userEntity);
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest.builder()
                                     .username(registerUserRequest.getUsername())
                                     .password(registerUserRequest.getPassword())
                                     .build();
        return this.login(authenticationRequest);
    }

    public UserEntity findUserAuthenticated() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                                                                           .getAuthentication();
        String username = (String) auth.getPrincipal();
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new CustomObjectNotFoundException("Incorrect username or password - User not found - 2"));
    }

    @Override
    public UserEntity updateUser(UpdateUserRequest updateUserRequest) {
        UserEntity userEntity = this.findUserAuthenticated();
        if (updateUserRequest.getName() != null && !updateUserRequest.getName()
                                                                     .isBlank()) {
            userEntity.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getDescription() != null && !updateUserRequest.getDescription()
                                                                            .isBlank()) {
            userEntity.setDescription(updateUserRequest.getDescription());
        }
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> searchUsersByUsernameOrName(String peopleToSearch) {
        return userRepository.findByUsernameLikeOrNameLikeAllIgnoreCase(peopleToSearch);
    }

    @Override
    public UserEntity findUserById(Long userId) {
        Optional<UserEntity> postEntityOptional = userRepository.findById(userId);
        if (postEntityOptional.isEmpty()) {
            throw new CustomObjectNotFoundException("post with " + userId + " not found");
        }
        return postEntityOptional.get();
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
