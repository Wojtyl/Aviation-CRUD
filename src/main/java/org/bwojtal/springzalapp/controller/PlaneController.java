package org.bwojtal.springzalapp.controller;

import lombok.RequiredArgsConstructor;
import org.bwojtal.springzalapp.dto.PlaneDTO;
import org.bwojtal.springzalapp.exception.BadRequestException;
import org.bwojtal.springzalapp.mapper.PlaneMapper;
import org.bwojtal.springzalapp.service.AirlineService;
import org.bwojtal.springzalapp.service.PlaneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planes")
public class PlaneController {
    private final PlaneService planeService;
    private final AirlineService airlineService;
    private final PlaneMapper planeMapper;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PlaneDTO>> getPlanes() {
        return ResponseEntity.ok(planeService.findAllPlanes());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PlaneDTO> getPlaneById(@PathVariable Long id) {
        PlaneDTO planeDTO = planeService.findById(id);

        return ResponseEntity.ok(planeDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlaneDTO> updatePlane(@PathVariable Long id, @RequestBody PlaneDTO planeDTO) {
        if (id == null) {
            throw new BadRequestException("Plane id is required");
        }

        PlaneDTO updatedPlane = planeMapper.planeToPlaneDTO(planeService.updatePlane(id, planeDTO));

        return ResponseEntity.ok(updatedPlane);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlaneDTO> partialUpdatePlane(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        if (id == null) {
            throw new BadRequestException("Plane id is required");
        }

        PlaneDTO updatedPlane = planeMapper.planeToPlaneDTO(planeService.partialUpdatePlane(id, updates));

        return ResponseEntity.ok(updatedPlane);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlaneDTO> createPlane(@RequestBody PlaneDTO planeDTO) {
        if (planeDTO == null) {
            throw new BadRequestException("You have to provide data");
        }

        if (!Objects.isNull(planeDTO.getAirlineId()) && !airlineService.checkIfAirlineExists(planeDTO.getAirlineId())) {
            throw new BadRequestException("Airline with that id does not exist");
        }

        PlaneDTO createdPlane = planeService.createPlane(planeDTO);

        return ResponseEntity.ok(createdPlane);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlaneById(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("No id provided");
        }

        if (!planeService.checkIfPlaneExists(id)) {
            throw new BadRequestException("There is no plane with that ID");

        }

        planeService.deletePlane(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
