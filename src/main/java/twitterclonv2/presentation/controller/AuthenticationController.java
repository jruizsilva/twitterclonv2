package twitterclonv2.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.business.service.AuthenticationService;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.domain.entity.UserEntity;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@SecurityRequirements
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
            description = "Authenticate an user using the username and the password",
            summary = "POST endpoint for login an user"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse jwtDto = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(jwtDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findUserAuthenticated() {
        return ResponseEntity.ok(authenticationService.findUserAuthenticated());
    }

    @GetMapping("/public-access")
    public String publicAccess() {
        return "publicAccess";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
