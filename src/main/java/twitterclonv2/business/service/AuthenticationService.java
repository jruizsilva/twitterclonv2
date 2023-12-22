package twitterclonv2.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                        authenticationRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserEntity userEntity = userRepository.findByUsername(authenticationRequest.getUsername())
                                              .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(userEntity);

        String jwt = jwtService.generateToken(userEntity,
                                              generateExtraClaims(userEntity));
        return new AuthenticationResponse(jwt);
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
}
