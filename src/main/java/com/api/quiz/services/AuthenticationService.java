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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.api.quiz.services.VerificationsService.*;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Modifying
    public ResponseEntity<Object> register(RegisterUserDto registrationData) {
        User newUser;
        if (!verifyDate(String.valueOf(registrationData.birthDate()))) {
            throw new BadRequestException("Invalid birthDate, put it in the format: dd-mm-yyyy");
        }
        LocalDate date = LocalDate.parse(registrationData.birthDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate currentDate = LocalDate.now();
        int userAge = calculatorAge(registrationData, currentDate);
        if (userAge <= 16) {
            throw new BadRequestException("Children under 16 cannot register for the quiz");
        }
        if (registrationData.password().equals(registrationData.confirmPassword())) {
            try {
                validate(registrationData.password());
            } catch (Exception ex) {
                throw new BadRequestException(ex.getMessage());
            }
            var verifyEmail = registrationData.email().split("@");
            if (verifyEmail.length != 2 || verifyEmail[0].isEmpty() || verifyEmail[1].isEmpty()) {
                throw new BadRequestException("Email address sent in an invalid format!");
            }
            if (this.userRepository.findByEmail(registrationData.email()).isPresent())
                throw new BadRequestException("Email already exists!");
            newUser = new User(registrationData, EncryptPasswordService.encryptPassword(registrationData.password()));
        } else {
            throw new BadRequestException("Passwords do not match!");
        }
        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Successfully!");
    }

    @Transactional
    public ResponseEntity<Object> login(LoginUserDto loginData) {
        if (loginData.password() == null) {
            throw new BadRequestException("Invalid data");
        }
        var user = this.userRepository.findByEmail(loginData.email()).orElseThrow(() -> new BadRequestException("The data's entered is invalid!"));
        if (!new BCryptPasswordEncoder().matches(loginData.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password!");
        }
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, loginData, user.getAuthorities()));
        return ResponseEntity.ok().body("Login Successfully");
    }
}
