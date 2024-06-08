package org.bwojtal.springzalapp.service;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dao.UserDAO;
import org.bwojtal.springzalapp.dto.UserDTO;
import org.bwojtal.springzalapp.exception.NotFoundException;
import org.bwojtal.springzalapp.mapper.UserMapper;
import org.bwojtal.springzalapp.model.User;
import org.bwojtal.springzalapp.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserDTO> findAllUsers() {
        return userMapper.userListToUserDTOs(userRepo.findAll());
    }

    public List<UserDTO> findUsersByRole(String role) {
        return userMapper.userListToUserDTOs(userDAO.findByRole(role));
    }

    public UserDTO findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("There is no user with id " + id));
        return userMapper.userToUserDTO(user);
    }
}
