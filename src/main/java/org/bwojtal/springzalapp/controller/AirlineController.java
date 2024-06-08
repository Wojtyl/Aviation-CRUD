package org.bwojtal.springzalapp.controller;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.AirlineDTO;
import org.bwojtal.springzalapp.dto.PlaneDTO;
import org.bwojtal.springzalapp.exception.BadRequestException;
import org.bwojtal.springzalapp.mapper.AirlineMapper;
import org.bwojtal.springzalapp.service.AirlineService;
import org.bwojtal.springzalapp.service.PlaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineService airlineService;
    private final AirlineMapper airlineMapper;
    private final PlaneService planeService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AirlineDTO>> getAirlines() {
        return ResponseEntity.ok(airlineService.findAllAirlineDTOs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AirlineDTO> getAirlines(@PathVariable Long id) {
       return ResponseEntity.ok(airlineService.getAirlineDTOById(id));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createAirline(@RequestBody AirlineDTO airlineDTO) {
        if (airlineDTO == null) {
            throw new BadRequestException("You must provide airline details");
        }

        if (airlineDTO.getName() == null) {
            throw new BadRequestException("You must provide airline name");
        }

        airlineService.createAirline(airlineDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}/plane/{planeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlaneDTO> addAirplaneToAirline(@PathVariable Long id, @PathVariable Long planeId) {
        PlaneDTO planeDTO = planeService.findById(planeId);

         if (planeDTO.getAirlineId() != null) {
             throw new BadRequestException("This plane is already assigned to an airline");
         }

         planeDTO.setAirlineId(id);

         planeService.updatePlane(planeId, planeDTO);

         return ResponseEntity.ok(planeDTO);
    }
}
