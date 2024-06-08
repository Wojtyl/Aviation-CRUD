package org.bwojtal.springzalapp.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = { "id" }, alphabetic = true)
public class UserDTO {
    private String username;
    private String role;
}
