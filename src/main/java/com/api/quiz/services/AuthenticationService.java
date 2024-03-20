package com.api.quiz.services;

import com.api.quiz.dtos.LoginUserDto;
import com.api.quiz.dtos.RegisterUserDto;
import com.api.quiz.entities.User;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.errors.exceptions.UnauthorizedException;
import com.api.quiz.repositories.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Modifying
    public ResponseEntity<Object> register(RegisterUserDto registrationData) {
        User newUser;

        if (!VerificationsService.verifyDate(String.valueOf(registrationData.birthDate()))) {
            throw new BadRequestException("Invalid birthDate, put it in the format: dd-mm-yyyy");
        }
        LocalDate date = LocalDate.parse(registrationData.birthDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        LocalDate currentDate = LocalDate.now();
        int userAge = VerificationsService.calculatorAge(registrationData, currentDate);
        if (userAge <= 16) {
            throw new BadRequestException("Children under 16 cannot register for the quiz");
        }

        if (registrationData.password().equals(registrationData.confirmPassword())) {
            try {
                VerificationsService.validate(registrationData.password());
            } catch (Exception ex) {
                throw new BadRequestException(ex.getMessage());
            }
            if (this.userRepository.findByEmail(registrationData.email()).isPresent())
                throw new BadRequestException("Email already exists!");

            newUser = new User(registrationData, passwordEncoder.encode(registrationData.password()));
        } else {
            throw new BadRequestException("Passwords do not match!");
        }

        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully!");
    }

    @Transactional
    public ResponseEntity<Object> login(LoginUserDto loginData) {

        var user = this.userRepository.findByEmail(loginData.email()).orElseThrow(() -> new BadRequestException("The data's entered is invalid!"));
        if (!new BCryptPasswordEncoder().matches(loginData.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password!");
        }

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginData.email(), loginData.password());

        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(securityContext);

        return ResponseEntity.ok().body("Login Successfully!");
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByfullName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
