package com.example.demowebapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id = null;
    @Column(length=50, nullable=false)
    private String modelName = "";

    @Column(length=50, nullable=false)
    private String SerialNumber = "";
    @Column(nullable=true)
    private Double currentPrice = null;
}
