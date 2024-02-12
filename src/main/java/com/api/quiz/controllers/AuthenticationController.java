package com.api.quiz.controllers;

import com.api.quiz.dtos.LoginUserDto;
import com.api.quiz.dtos.RegisterUserDto;
import com.api.quiz.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginUserDto loginUserDto) {
        return this.authenticationService.login(loginUserDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return this.authenticationService.register(registerUserDto);
    }
}
