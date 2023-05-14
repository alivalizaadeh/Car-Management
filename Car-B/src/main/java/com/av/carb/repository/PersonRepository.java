package com.av.carb.repository;

import com.av.carb.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person , Long> {
    Optional<Person> findByNationalCode(String nationalCode);
    void deleteByNationalCode(String nationalCode);
}