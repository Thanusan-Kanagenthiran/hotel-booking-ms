package com.tmkproperties.auth_server.controller;

import com.tmkproperties.auth_server.model.CustomUser;
import com.tmkproperties.auth_server.repository.CustomUserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CustomUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody CustomUser user) {
        try {
            String hashPwd = passwordEncoder.encode(user.getPwd());
            user.setPwd(hashPwd);
            CustomUser savedCustomer = userRepository.save(user);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User registration failed");
            }
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = ex.getMessage();

            if (errorMessage.contains("users_email_key")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User registration failed: This email is already in use.");
            } else if (errorMessage.contains("users_mobile_number_key")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User registration failed: This mobile number is already in use.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User registration failed: A data integrity violation occurred.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration. Please try again later.");
        }
    }
}
