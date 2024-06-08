package org.bwojtal.springzalapp.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = { "id" }, alphabetic = true)
public class AirlineDTO {
    private Long id;
    private String name;
    private List<PlaneDTO> planes;
}
