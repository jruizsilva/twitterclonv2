package twitterclonv2.business.service;

import twitterclonv2.domain.entity.UserEntity;

import java.util.Map;

public interface JwtService {
    String generateToken(UserEntity userEntity,
                         Map<String, Object> extraClaims);
    String extractUsernameFromJwt(String jwt);
}
