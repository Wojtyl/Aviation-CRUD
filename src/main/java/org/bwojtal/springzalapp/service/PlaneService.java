package org.bwojtal.springzalapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.PlaneDTO;
import org.bwojtal.springzalapp.entity.Airline;
import org.bwojtal.springzalapp.entity.Plane;
import org.bwojtal.springzalapp.exception.NotFoundException;
import org.bwojtal.springzalapp.mapper.PlaneMapper;
import org.bwojtal.springzalapp.repository.PlaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PlaneService {
    private final PlaneRepository planeRepository;
    private final PlaneMapper planeMapper;
    private final AirlineService airlineService;

    public List<PlaneDTO> findAllPlanes() {
        return planeMapper.planeListToPlaneDTOs(planeRepository.findAll());
    }

    public PlaneDTO getPlaneDTOById(Long id) {
        return planeMapper.planeToPlaneDTO(planeRepository.findById(id).orElseThrow(() -> new NotFoundException("Plane not found")));
    }

    public Plane getPlaneById(Long id) {
        return planeRepository.findById(id).orElseThrow(() -> new NotFoundException("Plane not found"));
    }

    @Transactional
    public Plane updatePlane(Long planeId, PlaneDTO planeDTO) {
        Plane plane = planeRepository.findById(planeId).orElseThrow(() -> new NotFoundException("Plane not found"));

        plane.setRegistration(planeDTO.getRegistration());
        plane.setBrand(planeDTO.getBrand());
        if (!Objects.isNull(planeDTO.getAirlineId())) {
            plane.setAirline(airlineService.findAirlineById(planeDTO.getAirlineId()));
        } else {
            plane.setAirline(null);
        }
        plane.setSeries(planeDTO.getSeries());

        return planeRepository.save(plane);
    }

    @Transactional
    public Plane partialUpdatePlane(Long planeId, Map<String, Object> updates) {
        Plane plane = planeRepository.findById(planeId).orElseThrow(() -> new NotFoundException("Plane not found"));

        if (updates.containsKey("registration")) {
            plane.setRegistration(updates.get("registration").toString());
        }
        if (updates.containsKey("brand")) {
            plane.setBrand(updates.get("brand").toString());
        }
        if (updates.containsKey("airlineId")) {
            if (Objects.isNull(updates.get("airline")) || updates.get("airline").toString().trim().isEmpty()) {
                plane.setAirline(null);
            }
            plane.setAirline(airlineService.findAirlineById(Long.parseLong(updates.get("airlineId").toString())));
        }
        if (updates.containsKey("series")) {
            plane.setSeries(updates.get("series").toString());
        }

        return planeRepository.save(plane);
    }

    public void deletePlane(Long id) {
        planeRepository.deleteById(id);
    }

    public PlaneDTO createPlane(PlaneDTO planeDTO) {
        Airline airline = null;
        if (planeDTO.getAirlineId() != null) {
            airline = airlineService.findAirlineById(planeDTO.getAirlineId());
        }
        Plane plane = planeMapper.planeDTOToPlane(planeDTO, airline);

        return planeMapper.planeToPlaneDTO(planeRepository.save(plane));
    }

    public boolean checkIfPlaneExists(Long id) {
        return planeRepository.findById(id).isPresent();
    }
}
