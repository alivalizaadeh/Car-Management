package com.av.cara.controller;

import com.av.cara.model.Car;
import com.av.cara.model.CarNullClass;
import com.av.cara.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cara/car")
@Slf4j
public class CarController {

    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Car> saveCar(@RequestBody Car car){
        Car saveCar = service.saveCar(car);
        if (saveCar instanceof CarNullClass){
            log.info("We found a car with this build number !!!");
            return new ResponseEntity<>(new CarNullClass() , HttpStatus.NOT_ACCEPTABLE);
        }else {
            log.info("New car created..");
            return new ResponseEntity<>(saveCar , HttpStatus.CREATED);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Car>> findAllCars(){
        return new ResponseEntity<>(service.carList() , HttpStatus.FOUND);
    }

    @GetMapping("/{buildNumber}")
    public ResponseEntity<Car> findCarByBuildNumber(@PathVariable("buildNumber") String buildNumber){
        Car car = service.findCarByBuildNumber(buildNumber);
        if(car instanceof CarNullClass){
            log.info("The car with build number : '{}' not found !!!" , buildNumber);
            return new ResponseEntity<>(new CarNullClass() , HttpStatus.NOT_FOUND);
        }else{
            log.info("The car with build number : '{}' founded : " , buildNumber);
            return new ResponseEntity<>(car, HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/{buildNumber}")
    public ResponseEntity<Car> deleteCarByBuildNumber(@PathVariable("buildNumber") String buildNumber){
        service.deleteCarByBuildNumber(buildNumber);
        log.info("The car with build number : '{} deleted !!!" , buildNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{buildNumber}")
    public ResponseEntity<Car> updateCarByBuildNumber(@PathVariable("buildNumber") String buildNumber,
                                                      @RequestBody Car car){
        Car updateCarByBuildNumber = service.updateCarByBuildNumber(car, buildNumber);
        if(updateCarByBuildNumber instanceof CarNullClass){
            log.info("The car with build number : '{}' not found !!!" , buildNumber);
            return new ResponseEntity<>(new CarNullClass() , HttpStatus.NOT_FOUND);
        }else{
            log.info("The car with build number : '{}' upgraded !!! : " , buildNumber);
            return new ResponseEntity<>(updateCarByBuildNumber, HttpStatus.UPGRADE_REQUIRED);
        }
    }
}
