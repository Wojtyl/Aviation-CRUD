package org.bwojtal.springzalapp.service;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.UserDTO;
import org.bwojtal.springzalapp.exception.BadRequestException;
import org.bwojtal.springzalapp.exception.NotFoundException;
import org.bwojtal.springzalapp.form.UserForm;
import org.bwojtal.springzalapp.mapper.UserMapper;
import org.bwojtal.springzalapp.entity.User;
import org.bwojtal.springzalapp.repository.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<GrantedAuthority> roles = new ArrayList<>();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        roles.add(new SimpleGrantedAuthority(user.getRole()));


        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole())
                .build();
    }


    public List<UserDTO> findAllUsers() {
        return userMapper.userListToUserDTOs(userRepo.findAll());
    }

    public UserDTO findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
        return userMapper.userToUserDTO(user);
    }

    public UserDTO createUser(UserForm userForm) {

        if (userForm.getUsername() == null) {
            throw new BadRequestException("You have to provide username");
        }

        if (userRepo.findByUsername(userForm.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        if (userForm.getPassword() == null) {
            throw new BadRequestException("You have to provide password");
        }

        if (userForm.getRole() == null) {
            throw new BadRequestException("You have to provide role");
        }

        if (userForm.getPassword().length() < 4) {
            throw new BadRequestException("Password must be at least 4 characters");
        }

        if (!Objects.equals(userForm.getRole(), "USER") && !userForm.getRole().equals("ADMIN")) {
            throw new BadRequestException("You provided wrong role");
        }

        User user = userMapper.userFormToUser(userForm);

        return userMapper.userToUserDTO(userRepo.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
        userRepo.delete(user);
    }
}
