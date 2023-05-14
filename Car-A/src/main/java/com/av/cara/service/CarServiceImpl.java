package com.av.cara.service;

import com.av.cara.CarAApplication;
import com.av.cara.model.Car;
import com.av.cara.model.CarNullClass;
import com.av.cara.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository repository;

    @Autowired
    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Car saveCar(Car car) {
        Car findCar = findCarByBuildNumber(car.getBuildNumber());
        return findCar instanceof CarNullClass ? repository.save(
                Car.builder().
                        name(CarAApplication.toStandard(car.getName())).
                        buildNumber(car.getBuildNumber()).
                        createdAt(car.getCreatedAt()).
                        price(car.getPrice()).
                        build()
        ):new CarNullClass();
    }

    @Override
    public Car findCarByBuildNumber(String buildNumber) {
        return repository.findByBuildNumber(buildNumber).orElse(new CarNullClass());
    }

    @Override
    public void deleteCarByBuildNumber(String buildNumber) {
        repository.deleteByBuildNumber(buildNumber);
    }

    @Override
    public Car updateCarByBuildNumber(Car car , String buildNumber) {
        Car findCar = findCarByBuildNumber(buildNumber);
        return ! (findCar instanceof CarNullClass) ? repository.saveAndFlush(
                Car.builder().
                        name(CarAApplication.toStandard(car.getName())).
                        buildNumber(buildNumber).
                        createdAt(car.getCreatedAt()).
                        price(car.getPrice()).
                        build()
        ):new CarNullClass();
    }

    @Override
    public List<Car> carList() {
        return repository.findAll();
    }

}
