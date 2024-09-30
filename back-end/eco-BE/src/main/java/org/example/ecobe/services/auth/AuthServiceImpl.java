package org.example.ecobe.services.auth;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecobe.dto.SignupRequest;
import org.example.ecobe.dto.UserDto;
import org.example.ecobe.enums.UserRole;
import org.example.ecobe.model.User;
import org.example.ecobe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder bEncoder;

    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        log.info("Creating user with email: {}", signupRequest.getEmail(),signupRequest.getName());

        User createdUser = userRepository.save(User.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(bEncoder.encode(signupRequest.getPassword()))
                .role(UserRole.CUSTOMER)
                .build());

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount() {
        log.info("Running application for the first time creates an Admin account with default info");
        Optional<User> adminAccountUser = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccountUser.isEmpty()) {
            log.info("Admin account created with email: admin@gmail.com and password: admin");
            userRepository.save(
                    User.builder()
                            .email("admin@gmail.com")
                            .name("admin")
                            .role(UserRole.ADMIN)
                            .password(bEncoder.encode("admin"))
                            .build()
            );
        }
    }
}
