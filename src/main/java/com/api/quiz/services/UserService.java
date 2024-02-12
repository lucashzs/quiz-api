package com.api.quiz.services;

import com.api.quiz.dtos.UserDto;
import com.api.quiz.entities.User;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.errors.exceptions.NotFoundException;
import com.api.quiz.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("Document Not Found! ID: " + id));
    }

    @Transactional
    public ResponseEntity<Object> update(UserDto userDto, Long id) {
        searchUser(id);
        User newUser = findById(id);
        newUser.setPassword(EncryptPasswordService.encryptPassword(userDto.password()));
        newUser.setEmail(userDto.password());
        newUser.setBirthDate(String.valueOf(userDto.birthDate()));
        newUser.setNickname(userDto.nickname());
        newUser.setUsername(userDto.username());
        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body("Update Successfully");
    }

    public ResponseEntity<Object> delete(Long id) {
        canDeleteUser(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new BadRequestException("Cannot delete as we cannot find this document!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Delete Successfully ID: %s", id));
    }

    public void canDeleteUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("You are not authorized to delete a user!");
        } else {
            userDetails = (UserDetails) authentication.getPrincipal();
            long authenticationId = Long.parseLong(userDetails.getUsername());
            if (!id.equals(authenticationId))
                throw new BadRequestException("A user can only delete himself");
        }
    }

    public void searchUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("You are not authorized to update a user!");
        } else {
            userDetails = (UserDetails) authentication.getPrincipal();
            long authenticationId = Long.parseLong(userDetails.getUsername());
            if (!id.equals(authenticationId)) {
                throw new BadRequestException("A user can only update for himself");
            }
        }
    }
}
