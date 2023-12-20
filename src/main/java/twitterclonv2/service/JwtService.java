package twitterclonv2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitterclonv2.entity.UserEntity;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.jwt.expiration-minutes}")
    private Long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserEntity userEntity,
                                Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts.builder()
                   .claims(extraClaims)
                   .subject(userEntity.getUsername())
                   .issuedAt(issuedAt)
                   .expiration(expiration)
                   .header()
                   .type("JWT")
                   .and()
                   .signWith(generateKey(),
                             Jwts.SIG.HS256)
                   .compact();
    }

    private SecretKey generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    public String extractUsernameFromJwt(String jwt) {
        return extractAllClaims(jwt)
                .getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                   .verifyWith(generateKey())
                   .build()
                   .parseSignedClaims(jwt)
                   .getPayload();
    }
}
