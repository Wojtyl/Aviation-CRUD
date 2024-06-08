package org.bwojtal.springzalapp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.bwojtal.springzalapp.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findByRole(String role) {

        return entityManager
                .createQuery("from User u where lower(u.role) = :role", User.class)
                .setParameter("role", role.toLowerCase())
                .getResultList();
    }
}
