package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.service.UserService;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findUserAuthenticated() {
        return ResponseEntity.ok(userService.findUserAuthenticated());
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PatchMapping("/username/{username}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody @Valid
                                                 UpdateUserRequest updateUserRequest,
                                                 @PathVariable String username
    ) {
        return ResponseEntity.ok(userService.updateUser(username,
                                                        updateUserRequest));
    }

    @GetMapping("/search/{peopleToSearch}")
    public ResponseEntity<List<UserEntity>> searchUsersByUsernameOrName(@PathVariable String peopleToSearch) {
        String search = "%" + peopleToSearch + "%";
        return ResponseEntity.ok(userService.searchUsersByUsernameOrName(search));
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent()
                             .build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntity> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @PatchMapping("/addFollower/{username}")
    public ResponseEntity<UserEntity> addFollower(@PathVariable String username) {
        return ResponseEntity.ok(userService.addFollower(username));
    }

    @PatchMapping("/removeFollower/{username}")
    public ResponseEntity<UserEntity> removeFollower(@PathVariable String username) {
        return ResponseEntity.ok(userService.removeFollower(username));
    }
}
