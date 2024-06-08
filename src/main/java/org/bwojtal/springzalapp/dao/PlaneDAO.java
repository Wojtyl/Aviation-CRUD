package org.bwojtal.springzalapp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.bwojtal.springzalapp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaneDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findByRole(String role) {

        return entityManager
                .createQuery("from Plane where", User.class)
                .setParameter("role", role.toLowerCase())
                .getResultList();
    }
}
