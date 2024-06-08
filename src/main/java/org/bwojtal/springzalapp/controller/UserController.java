package org.bwojtal.springzalapp.controller;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.UserDTO;
import org.bwojtal.springzalapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id) {
        UserDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<List<UserDTO>> getUserById(
            @PathVariable String role) {
        List<UserDTO> users = userService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }
}
