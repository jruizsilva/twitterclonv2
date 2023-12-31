package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.domain.dto.user.UserDto;
import twitterclonv2.domain.dto.user.request.UpdateUserRequest;

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
        System.out.println(search);
        return ResponseEntity.ok(userFacade.searchUsersByUsernameOrName(search));
    }
}
