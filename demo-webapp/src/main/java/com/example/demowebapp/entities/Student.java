package com.example.demowebapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length=20, nullable=false)
    private String lastName;
    @Column(length=20, nullable=false)
    private String firstName;
    @Column(length=50, nullable=false, unique = true)
    private String email;
}
