package com.av.carb.service;

import com.av.carb.model.Person;

import java.util.List;

public interface PersonService {

    Person savePerson(Person person);
    Person findPersonByNationalCode(String nationalCode);
    void deletePersonByNationalCode(String nationalCode);
    Person updatePersonByNationalCode(Person person , String nationalCode);
    List<Person> personList();

}
