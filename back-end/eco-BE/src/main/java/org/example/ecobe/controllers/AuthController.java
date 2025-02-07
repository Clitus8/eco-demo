package org.example.ecobe.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecobe.dto.AuthenticationRequest;
import org.example.ecobe.dto.SignupRequest;
import org.example.ecobe.dto.UserDto;
import org.example.ecobe.model.User;
import org.example.ecobe.repositories.UserRepository;
import org.example.ecobe.services.auth.AuthService;
import org.example.ecobe.services.jwt.UserDetailsServiceImpl;
import org.example.ecobe.utils.JwtUtil;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;


    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) {
        log.info("Received authentication request for user: {}", authenticationRequest.getUsername());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", authenticationRequest.getUsername(), e);
            throw new BadCredentialsException("incorrect user or pass");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> user = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (user.isPresent()) {
            try {
                response.getWriter().write(new JSONObject().put("userId", user.get().getId())
                        .put("role", user.get().getRole()).toString());
                response.addHeader("Access-Control-Expose-Headers", "Authorization");
                response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, "
                        + "X-Requested-With, Content-Type, Accent, X-Custom-header");
                log.info("User ID: {} - Role: {} - Token generated: {}", user.get().getId(), user.get().getRole(), jwt);
            } catch (Exception e) {
                log.error("Error writing response for user: {}", authenticationRequest.getUsername(), e);
            }
            String TOKEN_PREFIX = "Bearer ";
            String HEADER_STRING = "Authorization";
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        } else {
            log.warn("User not found: {}", authenticationRequest.getUsername());
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody SignupRequest signupRequest) {
        log.info("Received sign-up request for email: {}", signupRequest.getEmail());

        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            log.warn("User already exists with email: {}", signupRequest.getEmail());
            return new ResponseEntity<>("user already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        log.info("User created with email: {}", signupRequest.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
