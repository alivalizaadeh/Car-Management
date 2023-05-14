package com.av.carb.service;

import com.av.carb.model.Car;

import java.util.List;

public interface CarService {

    Car saveCar(Car car);
    Car findCarByBuildNumber(String buildNumber);
    void deleteCarByBuildNumber(String buildNumber);
    Car updateCarByBuildNumber(Car car , String buildNumber);
    List<Car> carList();
}
