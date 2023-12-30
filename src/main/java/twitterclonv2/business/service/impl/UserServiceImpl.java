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
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.UserRepository;

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

    public void registerUser(RegisterUserRequest registerUserRequest) {
        UserEntity userEntity =
                UserEntity.builder()
                          .name(registerUserRequest.getName())
                          .username(registerUserRequest.getUsername())
                          .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                          .role(Role.USER)
                          .build();
        userRepository.save(userEntity);
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
    public UserEntity updateUser(UserEntity userEntityRequest) {
        return userRepository.save(userEntityRequest);
    }
}
