package com.pozpl.nerannotator.auth.impl.user;

import com.pozpl.neraannotator.user.api.registration.IUserRegistrationService;
import com.pozpl.neraannotator.user.api.registration.SignupRequest;
import com.pozpl.neraannotator.user.api.registration.UserRegistrationResultDto;
import com.pozpl.nerannotator.auth.impl.jwt.JwtTokenUtil;
import com.pozpl.nerannotator.shared.exceptions.NerWebException;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserRegistrationService userRegistrationService;
    private final JwtTokenUtil jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          IUserRegistrationService userRegistrationService,
                          JwtTokenUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public AuthResultDto authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String jwt = jwtUtils.generateJwtToken(authentication);

            final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            final List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return AuthResultDto.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        }catch (AuthenticationException e){
            return AuthResultDto.error("Authentication error");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody final SignupRequest signUpRequest) {

        final Try<UserRegistrationResultDto> userRegTry = this.userRegistrationService.register(signUpRequest);
        final UserRegistrationResultDto registrationResultDto = userRegTry.getOrElseThrow(NerWebException::new);
        if (! registrationResultDto.isStatus()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(registrationResultDto.getErrorMessage()));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}