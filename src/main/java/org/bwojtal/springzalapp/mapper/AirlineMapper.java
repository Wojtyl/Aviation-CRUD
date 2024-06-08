package org.bwojtal.springzalapp.mapper;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.AirlineDTO;
import org.bwojtal.springzalapp.entity.Airline;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AirlineMapper {

    private final PlaneMapper planeMapper;

    public List<AirlineDTO> airlineListToAirlineDTOs(List<Airline> airlines) {
        return airlines.stream().map(this::airlineToAirlineDTO).toList();
    }

    public AirlineDTO airlineToAirlineDTO(Airline airline) {
        AirlineDTO airlineDTO = new AirlineDTO();

        airlineDTO.setName(airline.getName());
        airlineDTO.setId(airline.getId());
        airlineDTO.setPlanes(planeMapper.planeListToPlaneDTOs(airline.getPlanes()));

        return airlineDTO;
    }

    public Airline airlineDTOtoAirline(AirlineDTO airlineDTO) {
        Airline airline = new Airline();

        airline.setName(airlineDTO.getName());
        airline.setId(airlineDTO.getId());

        return airline;
    }

}
