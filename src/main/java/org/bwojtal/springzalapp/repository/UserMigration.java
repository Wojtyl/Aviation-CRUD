package org.bwojtal.springzalapp.repository;

import org.bwojtal.springzalapp.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class UserMigration implements CommandLineRunner  {

    @Autowired
    private UserRepo userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User(1L, "user", "password", "USER"));
            userRepository.save(new User(2L, "admin", "password", "ADMIN"));
        }
    }
}
