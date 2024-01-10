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
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173",
                                                      "https://clonapp.onrender.com",
                                                      "https://twitter-clon-frontend.vercel.app"));
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
                                                                       "uploads/profileImages/**")
                                                      .permitAll();

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "uploads/backgroundImages/**")
                                                      .permitAll();

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users/profile")
                                                      .hasAuthority(Permission.GET_USER_AUTHENTICATED.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.PATCH,
                                                                       "/users/username/**")
                                                      .hasAuthority(Permission.UPDATE_USER.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users")
                                                      .hasAuthority(Permission.FIND_ALL_USERS.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users/search/**")
                                                      .hasAuthority(Permission.SEARCH_USERS.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.PATCH,
                                                                       "/posts/**")
                                                      .hasAuthority(Permission.UPDATE_POST.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE,
                                                                       "/posts/**")
                                                      .hasAuthority(Permission.DELETE_POST.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/posts")
                                                      .hasAuthority(Permission.CREATE_ONE_POST.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/posts/username/**")
                                                      .hasAuthority(Permission.FIND_ALL_POSTS_BY_USERNAME.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/posts")
                                                      .hasAuthority(Permission.FIND_ALL_POSTS.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE,
                                                                       "/users/username/**")
                                                      .hasAuthority(Permission.DELETE_USER_BY_USERNAME.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,
                                                                       "/users/username/**")
                                                      .hasAuthority(Permission.FIND_USER_BY_USERNAME.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/upload/profileImage")
                                                      .hasAuthority(
                                                              Permission.UPLOAD_PROFILE_IMAGE.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST,
                                                                       "/upload/backgroundImage")
                                                      .hasAuthority(
                                                              Permission.UPLOAD_BACKGROUND_IMAGE.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/posts/[0-9]+/like",
                                                                                      HttpMethod.PATCH.name()))
                                                      .hasAuthority(Permission.CREATE_LIKE.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/posts/[0-9]+/removeLike",
                                                                                      HttpMethod.PATCH.name()))
                                                      .hasAuthority(Permission.DELETE_LIKE.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/users/[0-9]+/savePost",
                                                                                      HttpMethod.PATCH.name()))
                                                      .hasAuthority(Permission.ADD_POST_TO_POSTS_SAVED.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/users/[0-9]+/removePostSaved",
                                                                                      HttpMethod.PATCH.name()))
                                                      .hasAuthority(Permission.REMOVE_POST_FROM_POSTS_SAVED.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/posts/username/[a-z]+/postsCreated",
                                                                                      HttpMethod.GET.name()))
                                                      .hasAuthority(Permission.FIND_ALL_POSTS_CREATED_BY_USERNAME.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/posts/username/[a-z]+/postsLiked",
                                                                                      HttpMethod.GET.name()))
                                                      .hasAuthority(Permission.FIND_ALL_POSTS_LIKE_BY_USERNAME.name());

            authorizationManagerRequestMatcherRegistry.requestMatchers(
                                                              new RegexRequestMatcher("/posts/username/[a-z]+/postsSaved",
                                                                                      HttpMethod.GET.name()))
                                                      .hasAuthority(Permission.FIND_ALL_POSTS_SAVED_BY_USERNAME.name());

            authorizationManagerRequestMatcherRegistry.anyRequest()
                                                      .denyAll();
        };
    }
}
