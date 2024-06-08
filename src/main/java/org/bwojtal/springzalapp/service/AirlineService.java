package org.bwojtal.springzalapp.service;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.AirlineDTO;
import org.bwojtal.springzalapp.entity.Airline;
import org.bwojtal.springzalapp.exception.NotFoundException;
import org.bwojtal.springzalapp.mapper.AirlineMapper;
import org.bwojtal.springzalapp.repository.AirlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    public List<AirlineDTO> findAllAirlineDTOs() {
        return airlineMapper.airlineListToAirlineDTOs(airlineRepository.findAll());
    }

    public AirlineDTO getAirlineDTOById(Long id) {
        return airlineMapper.airlineToAirlineDTO(airlineRepository.findById(id).orElseThrow(() -> new NotFoundException("Airline with that ID not found")));
    }

    public void createAirline(AirlineDTO airlineDTO) {
        airlineRepository.save(airlineMapper.airlineDTOtoAirline(airlineDTO));
    }


    public boolean checkIfAirlineExists(Long airlineId) {
        return airlineRepository.findById(airlineId).isPresent();
    }

    public Airline findAirlineById(Long airlineId) {
        System.out.println("Ailine ID: !!!" + airlineId);
        return airlineRepository.findById(airlineId).orElseThrow(() -> new NotFoundException("Airline with that ID not found"));
    }
}
