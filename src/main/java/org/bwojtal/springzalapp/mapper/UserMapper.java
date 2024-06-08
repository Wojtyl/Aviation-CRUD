package org.bwojtal.springzalapp.mapper;

import org.bwojtal.springzalapp.dto.UserDTO;
import org.bwojtal.springzalapp.model.User;
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

        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
