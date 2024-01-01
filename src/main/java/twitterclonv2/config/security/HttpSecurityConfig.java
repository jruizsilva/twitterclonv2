package twitterclonv2.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import twitterclonv2.common.util.Permission;
import twitterclonv2.config.security.filter.JwtAuthenticationFilter;
import twitterclonv2.config.security.handler.CustomAccessDeniedHandler;
import twitterclonv2.config.security.handler.CustomAuthenticationEntryPoint;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class HttpSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                                     UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        httpSecurity.authorizeHttpRequests(buildRequestMatchers());
        httpSecurity.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint);
            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(customAccessDeniedHandler);
        });
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                                         configuration);
        return source;
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> buildRequestMatchers() {
        return authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers("/error")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers("/h2-console/**")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers("/v3/api-docs/**")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers("/swagger-ui/**")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/auth/**")
                                                      .permitAll();
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users/profile")
                                                      .hasAuthority(Permission.GET_USER_AUTHENTICATED.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.PATCH,
                                                                       "/users")
                                                      .hasAuthority(Permission.UPDATE_USER.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users")
                                                      .hasAuthority(Permission.FIND_ALL_USERS.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users/search/**")
                                                      .hasAuthority(Permission.SEARCH_USERS.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.PUT,
                                                                       "/posts/**")
                                                      .hasAuthority(Permission.UPDATE_POST.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE,
                                                                       "/posts/**")
                                                      .hasAuthority(Permission.DELETE_POST.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.PATCH,
                                                                       "/users/like/**")
                                                      .hasAuthority(Permission.LIKE_POST.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(new RegexRequestMatcher("/posts/[0-9]+/like",
                                                                                               HttpMethod.PATCH.name()))
                                                      .hasAuthority(Permission.LIKE_POST.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.PATCH,
                                                                       "/posts/[0-9]/like")
                                                      .hasAuthority(Permission.REMOVE_LIKE_IN_POST.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/posts")
                                                      .hasAuthority(Permission.CREATE_ONE_POST.name());
            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/posts")
                                                      .permitAll();

            authorizationManagerRequestMatcherRegistry.anyRequest()
                                                      .denyAll();
        };
    }
}
