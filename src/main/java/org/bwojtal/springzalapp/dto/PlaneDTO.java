package org.bwojtal.springzalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaneDTO {
    private Long id;
    private String brand;
    private String series;
    private String registration;
    private Long airlineId;
}
