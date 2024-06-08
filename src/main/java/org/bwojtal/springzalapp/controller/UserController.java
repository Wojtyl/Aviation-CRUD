package org.bwojtal.springzalapp.controller;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.UserDTO;
import org.bwojtal.springzalapp.exception.BadRequestException;
import org.bwojtal.springzalapp.form.UserForm;
import org.bwojtal.springzalapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id) {
        UserDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserForm userForm
            ) {

        if (userForm == null) {
            throw new BadRequestException("No form provided");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userForm));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long id
    ) {
        if (id == null) {
            throw new BadRequestException("No id provided");
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
