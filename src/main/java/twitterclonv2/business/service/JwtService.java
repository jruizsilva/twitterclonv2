package twitterclonv2.business.service;

import twitterclonv2.domain.entity.UserEntity;

public interface JwtService {
    String generateToken(UserEntity userEntity);
    String extractUsernameFromJwt(String jwt);
}
