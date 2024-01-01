package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;
import twitterclonv2.domain.entity.UserEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> findUserAuthenticated() {
        return ResponseEntity.ok(userFacade.findUserAuthenticated());
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userFacade.findAllUsers());
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid
                                              UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userFacade.updateUser(updateUserRequest));
    }

    @GetMapping("/search/{peopleToSearch}")
    public ResponseEntity<List<UserDto>> searchUsersByUsernameOrName(@PathVariable String peopleToSearch) {
        String search = "%" + peopleToSearch + "%";
        return ResponseEntity.ok(userFacade.searchUsersByUsernameOrName(search));
    }

    @PatchMapping("/like/{postId}")
    public ResponseEntity<UserEntity> toggleUserLikeByPostId(@PathVariable Long postId) {
        UserEntity userEntity = userFacade.toggleUserLikeByPostId(postId);
        return ResponseEntity.noContent()
                             .build();
    }
}
