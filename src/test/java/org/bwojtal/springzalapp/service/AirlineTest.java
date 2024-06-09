package org.bwojtal.springzalapp.service;

import org.bwojtal.springzalapp.dto.AirlineDTO;
import org.bwojtal.springzalapp.entity.Airline;
import org.bwojtal.springzalapp.mapper.AirlineMapper;
import org.bwojtal.springzalapp.repository.AirlineRepository;
import org.bwojtal.springzalapp.repository.PlaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AirlineTest {
    AirlineRepository airlineRepository;
    PlaneRepository planeRepository;
    AirlineMapper airlineMapper;
    AirlineService airlineService;

    @BeforeEach
    void init() {
        airlineRepository = mock(AirlineRepository.class);
        airlineMapper = mock(AirlineMapper.class);
        airlineService = new AirlineService(airlineRepository, airlineMapper, planeRepository);
    }

    @Test
    void shouldAddPlaneToRepository() {
        when(airlineRepository.save(any(Airline.class)))
                .thenAnswer(invocationOnMock -> {
                    Airline airline = invocationOnMock.getArgument(0, Airline.class);
                    airline.setId(1L);
                    return airline;
                });


        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setName("Test airline");

        Airline airline = new Airline();
        airline.setName(airlineDTO.getName());

        when(airlineMapper.airlineDTOtoAirline(any(AirlineDTO.class))).thenReturn(airline);
        when(airlineMapper.airlineToAirlineDTO(any(Airline.class))).thenAnswer(invocationOnMock -> {
            Airline savedAirline = invocationOnMock.getArgument(0, Airline.class);
            AirlineDTO resultDTO = new AirlineDTO();
            resultDTO.setId(savedAirline.getId());
            resultDTO.setName(savedAirline.getName());
            return resultDTO;
        });


        AirlineDTO createdAirlineDTO = airlineService.createAirline(airlineDTO);

        verify(airlineRepository).save(any(Airline.class));
        assertNotNull(createdAirlineDTO);
        assertEquals(1L, createdAirlineDTO.getId());
        assertEquals("Test airline", createdAirlineDTO.getName());
    }
}
