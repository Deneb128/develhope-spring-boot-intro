package com.example.demowebapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long Id;
    @Column(length=50, nullable=false)
    private String name = "";

    @Column(length=50, nullable=false)
    private String surname = "";
    @Column(nullable=false)
    private Boolean isWorking = false;
}
