package twitterclonv2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import twitterclonv2.config.security.filter.JwtAuthenticationFilter;
import twitterclonv2.common.util.Permission;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                                     UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        httpSecurity.authorizeHttpRequests(buildRequestMatchers());
        return httpSecurity.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> buildRequestMatchers() {
        return authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/auth/authenticate")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/auth/public-access")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/auth/test")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers("/error")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers("/h2-console/**")
                                                      .permitAll();

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/products")
                                                      .hasAuthority(Permission.READ_ALL_PRODUCTS.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/products")
                                                      .hasAuthority(Permission.SAVE_ONE_PRODUCT.name());

            authorizationManagerRequestMatcherRegistry.anyRequest()
                                                      .denyAll();
        };
    }
}
