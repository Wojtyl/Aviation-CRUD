package org.bwojtal.springzalapp.repository;

import org.bwojtal.springzalapp.entity.Airline;
import org.bwojtal.springzalapp.entity.Plane;
import org.bwojtal.springzalapp.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataMigration implements CommandLineRunner  {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Override
    public void run(String... args) {
//        if (userRepository.count() == 0) {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setRole("USER");

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setRole("ADMIN");

        userRepository.save(user);
        userRepository.save(admin);

        Airline airline = new Airline();
        airline.setName("airline");

        airlineRepository.save(airline);

        Plane boeing = new Plane();
        boeing.setBrand("Boeing");
        boeing.setSeries("737-800");
        boeing.setRegistration("SP-ABC");
        boeing.setAirline(airline);

        Plane airbus = new Plane();
        airbus.setBrand("Airbus");
        airbus.setSeries("A320");
        airbus.setRegistration("EG-SFW");

        planeRepository.save(boeing);
        planeRepository.save(airbus);
    }
}
