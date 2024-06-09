package org.bwojtal.springzalapp.controller;

import org.bwojtal.springzalapp.repository.PlaneRepository;
import org.bwojtal.springzalapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaneControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepo;
    @Autowired
    private PlaneRepository planeRepository;

    @AfterEach
        void clean() {
            userRepo.deleteAll();
        }

        @Test
        @WithMockUser(roles = {"ADMIN"})
        void shouldCreateNewPlane() throws Exception {
            long countBefore = planeRepository.count();

            mockMvc.perform(post("/planes")
                    .content("{\"brand\":\"testBrand\",\"series\":\"testSeries\"}")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is(200));

            long countAfter = planeRepository.count();

            Assertions.assertEquals(countBefore + 1, countAfter);
        }

        @Test
        @WithMockUser
        void shouldNotDeletePlaneAsUser() throws Exception {
            long countBefore = planeRepository.count();

            mockMvc.perform(delete("/planes/1"))
                    .andExpect(status().is(403));

            long countAfter = planeRepository.count();

            Assertions.assertEquals(countBefore, countAfter);
        }

}
