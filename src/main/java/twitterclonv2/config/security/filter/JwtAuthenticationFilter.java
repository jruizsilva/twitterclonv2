package twitterclonv2.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import twitterclonv2.entity.UserEntity;
import twitterclonv2.repository.UserRepository;
import twitterclonv2.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,
                                 response);
            return;
        }

        String jwt = authorizationHeader.replace("Bearer ",
                                                 "");
        String username = jwtService.extractUsernameFromJwt(jwt);
        UserEntity userEntity = userRepository.findByUsername(username)
                                              .orElseThrow(() -> new RuntimeException("User not found"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                                                                                                          userEntity.getPassword(),
                                                                                                          userEntity.getAuthorities());
        SecurityContextHolder.getContext()
                             .setAuthentication(authenticationToken);

        filterChain.doFilter(request,
                             response);
    }
}
