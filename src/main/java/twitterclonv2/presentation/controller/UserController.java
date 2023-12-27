package twitterclonv2.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.domain.dto.user.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> findUserAuthenticated() {
        return ResponseEntity.ok(userFacade.findUserAuthenticated());
    }
}
