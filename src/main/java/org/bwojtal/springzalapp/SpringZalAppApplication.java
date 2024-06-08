package org.bwojtal.springzalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.bwojtal.springzalapp.repository"})
@EntityScan(basePackages = {"org.bwojtal.springzalapp.entity"})
public class SpringZalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringZalAppApplication.class, args);
    }

}
