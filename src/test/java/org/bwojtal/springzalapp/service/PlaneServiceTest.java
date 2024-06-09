package org.bwojtal.springzalapp.service;

import org.bwojtal.springzalapp.dto.PlaneDTO;
import org.bwojtal.springzalapp.entity.Airline;
import org.bwojtal.springzalapp.entity.Plane;
import org.bwojtal.springzalapp.exception.NotFoundException;
import org.bwojtal.springzalapp.mapper.PlaneMapper;
import org.bwojtal.springzalapp.repository.PlaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaneServiceTest {

    private PlaneRepository planeRepository;
    private PlaneMapper planeMapper;
    private AirlineService airlineService;
    private PlaneService planeService;

    @BeforeEach
    void init() {
        planeRepository = mock(PlaneRepository.class);
        planeMapper = mock(PlaneMapper.class);
        airlineService = mock(AirlineService.class);
        planeService = new PlaneService(planeRepository, planeMapper, airlineService);
    }

    @Test
    void shouldAddPlaneToRepository() {
        when(planeRepository.save(any(Plane.class)))
                .thenAnswer(invocationOnMock -> {
                    Plane plane = invocationOnMock.getArgument(0, Plane.class);
                    plane.setId(1L);
                    return plane;
                });

        Airline airline = new Airline();
        airline.setId(1L);

        when(airlineService.findAirlineById(1L)).thenReturn(airline);

        PlaneDTO planeDTO = new PlaneDTO();
        planeDTO.setRegistration("ABC123");
        planeDTO.setBrand("Boeing");
        planeDTO.setAirlineId(1L);
        planeDTO.setSeries("737");

        Plane plane = new Plane();
        plane.setRegistration(planeDTO.getRegistration());
        plane.setBrand(planeDTO.getBrand());
        plane.setSeries(planeDTO.getSeries());
        plane.setAirline(airline);

        when(planeMapper.planeDTOToPlane(any(PlaneDTO.class), any(Airline.class))).thenReturn(plane);
        when(planeMapper.planeToPlaneDTO(any(Plane.class))).thenReturn(planeDTO);

        PlaneDTO createdPlaneDTO = planeService.createPlane(planeDTO);

        verify(planeRepository).save(any(Plane.class));
        assertNotNull(createdPlaneDTO);
        assertEquals(1L, createdPlaneDTO.getAirlineId());
        assertEquals("ABC123", createdPlaneDTO.getRegistration());
        assertEquals("Boeing", createdPlaneDTO.getBrand());
        assertEquals("737", createdPlaneDTO.getSeries());
    }

    @Test
    void testGetPlaneDTOById_ShouldThrowNotFoundException() {
        when(planeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            planeService.getPlaneDTOById(1L);
        });

        String expectedMessage = "Plane not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(planeRepository).findById(1L);
    }
}

