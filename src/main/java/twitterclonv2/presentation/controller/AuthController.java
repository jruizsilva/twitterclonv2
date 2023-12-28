package twitterclonv2.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitterclonv2.business.facade.UserFacade;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.RegisterUserRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController")
@SecurityRequirements
public class AuthController {
    private final UserFacade userFacade;

    @Operation(
            description = "Authenticate an user using the username and the password",
            summary = "POST endpoint for login an user"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws Exception {
        AuthenticationResponse jwtDto = userFacade.authenticate(authenticationRequest);
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        userFacade.registerUser(registerUserRequest);
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest.builder()
                                     .username(registerUserRequest.getUsername())
                                     .password(registerUserRequest.getPassword())
                                     .build();
        AuthenticationResponse authenticationResponse =
                userFacade.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(authenticationResponse);
    }
}
