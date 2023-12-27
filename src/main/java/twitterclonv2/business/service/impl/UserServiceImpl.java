package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.ObjectNotFoundException;
import twitterclonv2.common.util.Role;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                        authenticationRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserEntity userEntity = userRepository.findByUsername(authenticationRequest.getUsername())
                                              .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(userEntity);

        String jwt = jwtServiceImpl.generateToken(userEntity,
                                                  generateExtraClaims(userEntity));
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

    private Map<String, Object> generateExtraClaims(UserEntity userEntity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",
                        userEntity.getName());
        extraClaims.put("role",
                        userEntity.getRole()
                                  .name());
        extraClaims.put("permissions",
                        userEntity.getAuthorities());

        return extraClaims;
    }

    public UserEntity findUserAuthenticated() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                                                                           .getAuthentication();
        String username = (String) auth.getPrincipal();
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }
}