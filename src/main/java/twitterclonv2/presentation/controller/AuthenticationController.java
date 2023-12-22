package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;
import twitterclonv2.business.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse jwtDto = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(jwtDto);
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
