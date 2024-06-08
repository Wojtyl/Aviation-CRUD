package org.bwojtal.springzalapp.mapper;

import org.bwojtal.springzalapp.dto.UserDTO;
import org.bwojtal.springzalapp.entity.User;
import org.bwojtal.springzalapp.form.UserForm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public List<UserDTO> userListToUserDTOs(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();

        users.forEach(user -> userDTOs.add(userToUserDTO(user)));

        return userDTOs;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public User userFormToUser(UserForm userForm) {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setRole(userForm.getRole());

        return user;
    }
}
