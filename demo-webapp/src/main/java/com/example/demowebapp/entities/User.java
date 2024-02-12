package com.example.demowebapp.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long Id;
    @Column(length=50, nullable=false)
    private String nickname = "";
    @Column(length=100 /*hash length*/, nullable=false)
    private String password = "";
    @Column(length=50, nullable=false)
    private String email = "";
    @Column(nullable=false)
    private Boolean isBanned = false;
}
