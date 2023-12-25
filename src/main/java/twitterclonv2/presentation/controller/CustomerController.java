package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitterclonv2.business.service.AuthenticationService;
import twitterclonv2.business.service.CustomerService;
import twitterclonv2.domain.dto.user.request.AuthenticationRequest;
import twitterclonv2.domain.dto.user.request.CreateUserEntityRequest;
import twitterclonv2.domain.dto.user.response.AuthenticationResponse;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> createOneCustomer(@RequestBody @Valid CreateUserEntityRequest createUserEntityRequest) {
        customerService.createOneCustomer(createUserEntityRequest);
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest.builder()
                                     .username(createUserEntityRequest.getUsername())
                                     .password(createUserEntityRequest.getPassword())
                                     .build();
        AuthenticationResponse authenticationResponse =
                authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(authenticationResponse);
    }
}
