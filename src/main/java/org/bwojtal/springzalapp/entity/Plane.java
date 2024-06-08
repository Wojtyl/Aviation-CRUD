package org.bwojtal.springzalapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String series;
    private String registration;

    @ManyToOne
    @JoinColumn(name = "airline_id")
//    @JsonBackReference
    @JsonIdentityReference(alwaysAsId = true)
    private Airline airline;
}
