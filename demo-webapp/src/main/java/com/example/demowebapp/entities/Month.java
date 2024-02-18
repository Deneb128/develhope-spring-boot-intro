package com.example.demowebapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Month {
    @Id
    private Integer monthNumber;
    private String englishName;
    private String italianName;
    private String germanName;
}
