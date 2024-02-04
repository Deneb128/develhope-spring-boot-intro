package com.example.demowebapp.controllers;

import com.example.demowebapp.entities.Car;
import com.example.demowebapp.repositories.CarRepository;
import com.example.demowebapp.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping()
    public Car addNewCar(@RequestBody Car car){
        return carService.addNewCar(car);
    }

    @GetMapping()
    public List<Car> getCarList(){
        return carService.getCarList();
    }

    @GetMapping(path = "/{id}")
    public Car getCarByID(@PathVariable Long id){
        return carService.getCarByID(id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        if(carService.getCarByID(id).getId() != null) {
            carService.deleteCar(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping()
    public void deleteAllCars(){
        carService.deleteAllCars();
    }
}
