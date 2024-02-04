package com.example.demowebapp.services;

import com.example.demowebapp.entities.Car;
import com.example.demowebapp.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car addNewCar(Car car){
        return carRepository.saveAndFlush(car);
    }

    public List<Car> getCarList(){
        return carRepository.findAll();
    }
    public Car getCarByID(Long id){
        return carRepository.findById(id).orElse(new Car());
    }

    public Car updateTypeFromId(Long id, String type){
        if (carRepository.existsById(id)){
            Car car = carRepository.findById(id).get();
            car.setType(type);
            return carRepository.saveAndFlush(car);
        } else {
            return new Car();
        }
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

    public void deleteAllCars(){
        carRepository.deleteAll();
    }
}
