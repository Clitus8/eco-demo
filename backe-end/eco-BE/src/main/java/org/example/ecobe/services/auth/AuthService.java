package org.example.ecobe.services.auth;

import org.example.ecobe.dto.SignupRequest;
import org.example.ecobe.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

    void createAdminAccount();
}
