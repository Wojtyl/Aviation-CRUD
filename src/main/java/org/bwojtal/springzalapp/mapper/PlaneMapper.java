package org.bwojtal.springzalapp.mapper;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.PlaneDTO;
import org.bwojtal.springzalapp.entity.Airline;
import org.bwojtal.springzalapp.entity.Plane;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class PlaneMapper {

    public List<PlaneDTO> planeListToPlaneDTOs(List<Plane> planes) {
        List<PlaneDTO> planeDTOS = new ArrayList<>();

        planes.forEach(plane -> planeDTOS.add(planeToPlaneDTO(plane)));

        return planeDTOS;
    }

    public PlaneDTO planeToPlaneDTO(Plane plane) {
        PlaneDTO planeDTO = new PlaneDTO();

        planeDTO.setId(plane.getId());
        planeDTO.setBrand(plane.getBrand());
        planeDTO.setSeries(plane.getSeries());
        planeDTO.setRegistration(plane.getRegistration());
        planeDTO.setAirlineId(Objects.isNull(plane.getAirline()) ? null : plane.getAirline().getId());

        return planeDTO;
    }

    public Plane planeDTOToPlane(PlaneDTO planeDTO, Airline airline) {
        Plane plane = new Plane();

        plane.setId(planeDTO.getId());
        plane.setBrand(planeDTO.getBrand());
        plane.setSeries(planeDTO.getSeries());
        plane.setRegistration(planeDTO.getRegistration());
        plane.setAirline(airline);

        return plane;
    }
}
