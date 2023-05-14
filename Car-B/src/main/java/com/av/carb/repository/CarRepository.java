package com.av.carb.repository;

import com.av.carb.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByBuildNumber(String buildNumber);
    void deleteByBuildNumber(String buildNumber);

}
