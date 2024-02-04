package com.example.demowebapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="programming_languages")
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id = null;
    @Column(length=50, nullable=false)
    private String name = "";

    @Column(length=50, nullable=true)
    private LocalDate firstAppearance = null;
    @Column(length=50, nullable=false)
    private String inventor = null;
}
