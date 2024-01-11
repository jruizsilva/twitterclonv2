package twitterclonv2.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitterclonv2.business.service.JwtService;
import twitterclonv2.business.service.UserService;
import twitterclonv2.common.exception.CustomObjectNotFoundException;
import twitterclonv2.common.util.Role;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;
import twitterclonv2.persistence.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public AuthenticationResponse register(RegisterUserRequest registerUserRequest) {
        UserEntity userEntity =
                UserEntity.builder()
                          .name(registerUserRequest.getName())
                          .username(registerUserRequest.getUsername())
                          .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                          .role(Role.USER)
                          .postsCreated(Collections.emptyList())
                          .postsSaved(Collections.emptyList())
                          .postsLiked(Collections.emptyList())
                          .build();
        userRepository.save(userEntity);
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest.builder()
                                     .username(registerUserRequest.getUsername())
                                     .password(registerUserRequest.getPassword())
                                     .build();
        return this.login(authenticationRequest);
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
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> searchUsersByUsernameOrName(String peopleToSearch) {
        return userRepository.findByUsernameLikeOrNameLikeAllIgnoreCase(peopleToSearch);
    }

    @Override
    public UserEntity findUserById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomObjectNotFoundException("user with id " + userId + " not found");
        }
        return userOptional.get();
    }

    public UserEntity findUserByUsername(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new CustomObjectNotFoundException("user with username" + username + " not found");
        }
        return userOptional.get();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        UserEntity userToDelete = this.findUserByUsername(username);
        if (isNotAdmin() && usernameIsNotEqualToUserAuthenticated(username)) {
            throw new RuntimeException("Acceso denegado");
        }
        // Desasociar el usuario de los posts que ha creado
        userToDelete.getPostsCreated()
                    .forEach(post -> post.setUser(null));

        // Desasociar el usuario de los posts a los que les ha dado like
        userToDelete.getPostsLiked()
                    .forEach(post -> post.getLikedByUsers()
                                         .remove(userToDelete));

        // Desasociar el usuario de los posts que ha guardado
        userToDelete.getPostsSaved()
                    .forEach(post -> post.getSavedByUsers()
                                         .remove(userToDelete));

        // Limpiar las colecciones asociadas al usuario
        userToDelete.getPostsCreated()
                    .clear();
        userToDelete.getPostsLiked()
                    .clear();
        userToDelete.getPostsSaved()
                    .clear();

        // Sincronizar la entidad con la base de datos
        userRepository.save(userToDelete);

        userRepository.delete(userToDelete);
    }

    @Override
    public UserEntity addFollower(String username) {
        UserEntity userAuthenticated = this.findUserAuthenticated();
        UserEntity followerToAdd = this.findUserByUsername(username);

        if (Objects.equals(userAuthenticated.getUsername(),
                           followerToAdd.getUsername())) {
            System.out.println("can't follow yourself");
            throw new RuntimeException("can't follow yourself");
        }
        List<UserEntity> followers = userAuthenticated.getFollowers();
        if (!followers.isEmpty()) {
            Optional<UserEntity> followerAlreadyAdded = followers.stream()
                                                                 .filter(userEntity -> Objects.equals(userEntity.getUsername(),
                                                                                                      followerToAdd.getUsername()))
                                                                 .findFirst();
            if (followerAlreadyAdded.isPresent()) {
                System.out.println("follow already added");
                throw new RuntimeException("follow already added");
            }
        }
        followers.add(followerToAdd);
        userAuthenticated.setFollowers(followers);
        return userRepository.save(userAuthenticated);
    }

    @Override
    public UserEntity removeFollower(String username) {
        UserEntity userAuthenticated = this.findUserAuthenticated();
        UserEntity followerToRemove = this.findUserByUsername(username);

        List<UserEntity> followers = userAuthenticated.getFollowers();
        if (followers.isEmpty()) {
            System.out.println("there isn't followers to remove");
            return userAuthenticated;
        }
        Optional<UserEntity> followerToRemoveOptional =
                followers.stream()
                         .filter(userEntity -> Objects.equals(userEntity.getUsername(),
                                                              followerToRemove.getUsername()))
                         .findFirst();
        if (followerToRemoveOptional.isEmpty()) {
            System.out.println("can't remove a follower doesn't exists");
            return userAuthenticated;
        }
        followers.remove(followerToRemove);
        userAuthenticated.setFollowers(followers);
        return userRepository.save(userAuthenticated);
    }

    @Override
    public UserEntity updateUser(String username,
                                 UpdateUserRequest updateUserRequest) {
        UserEntity userToUpdate = this.findUserByUsername(username);
        if (isNotAdmin() && usernameIsNotEqualToUserAuthenticated(username)) {
            throw new RuntimeException("Acceso denegado");
        }
        if (updateUserRequest.getName() != null && !updateUserRequest.getName()
                                                                     .isBlank()) {
            userToUpdate.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getDescription() != null && !updateUserRequest.getDescription()
                                                                            .isBlank()) {
            userToUpdate.setDescription(updateUserRequest.getDescription());
        }
        return userRepository.save(userToUpdate);
    }

    private boolean isNotAdmin() {
        UserEntity userAuthenticated = this.findUserAuthenticated();

        return userAuthenticated.getAuthorities()
                                .stream()
                                .noneMatch(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(),
                                                                              "ROLE_ADMINISTRATOR"));
    }

    private boolean usernameIsNotEqualToUserAuthenticated(String username) {
        UserEntity userAuthenticated = this.findUserAuthenticated();
        return !Objects.equals(username,
                               userAuthenticated.getUsername());
    }
}
