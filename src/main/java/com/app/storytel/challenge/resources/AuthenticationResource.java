package com.app.storytel.challenge.resources;

import com.app.storytel.challenge.auth.JwtTokenProvider;
import com.app.storytel.challenge.model.LoginInformation;
import com.app.storytel.challenge.payload.request.LoginInformationRequest;
import com.app.storytel.challenge.payload.request.SignInRequest;
import com.app.storytel.challenge.payload.response.JwtAuthenticationResponse;
import com.app.storytel.challenge.payload.response.LoginInformationResponse;
import com.app.storytel.challenge.service.LoginInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Authentication class that serves REST services for new user registration and
 * sign in functionality. I've included these two here for simplicity and since
 * there isn't much going on in both methods
 */
@Slf4j
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final LoginInformationService loginInformationService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationResource(AuthenticationManager authenticationManager, LoginInformationService loginRepository,
            JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.loginInformationService = loginRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method is used to user authentication
     *
     * @param signInRequest - login json object
     * @return - ResponseEntity containing operation outcome and meta data of
     * user making the request
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<String> authenticateUser(
            @Valid @RequestBody SignInRequest signInRequest) {

        log.info("Called sign-in with {}", signInRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmailAddress(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        JwtAuthenticationResponse holder = new JwtAuthenticationResponse(jwt);

        return new ResponseEntity<>("Bearer " + holder.getAccessToken(), HttpStatus.OK);

    }

    /**
     * REST Method to register new user
     *
     * @param loginInformationRequest
     * @return ResponseEntity
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<LoginInformationResponse> registerUser(
            @Valid @RequestBody LoginInformationRequest loginInformationRequest) {
        log.info("Registering new app user: {}", loginInformationRequest);

        loginInformationRequest.setPassword(this.passwordEncoder.encode(loginInformationRequest.getPassword()));
        LoginInformation loginInformation
                = this.loginInformationService.saveNewLoginInformation(loginInformationRequest);

        if (loginInformation != null) {
            log.info("Created new login information successfully");

            return new ResponseEntity<>(new LoginInformationResponse(loginInformation.getId(),
                    loginInformation.getEmailAddress(), loginInformation.getApplicationRole().getRoleName(),
                    loginInformation.getCreated(), loginInformation.getModified()), HttpStatus.CREATED);
        } else {
            log.info("Failed to save new login information");
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
