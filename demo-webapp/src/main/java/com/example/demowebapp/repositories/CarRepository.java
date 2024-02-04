package com.example.demowebapp.repositories;

import com.example.demowebapp.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
