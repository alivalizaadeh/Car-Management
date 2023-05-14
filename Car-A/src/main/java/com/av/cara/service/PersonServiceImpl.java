package com.av.cara.service;

import com.av.cara.CarAApplication;
import com.av.cara.model.PersonNullClass;
import com.av.cara.repository.PersonRepository;
import com.av.cara.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person savePerson(Person person) {
        Person findPerson = findPersonByNationalCode(person.getNationalCode());
        return findPerson instanceof PersonNullClass ? repository.save(
                Person.builder().
                        firstName(CarAApplication.toStandard(person.getFirstName())).
                        lastName(CarAApplication.toStandard(person.getLastName())).
                        nationalCode(person.getNationalCode()).
                        dob(person.getDob()).
                        build()
        ):new PersonNullClass();
    }

    @Override
    public Person findPersonByNationalCode(String nationalCode) {
        return repository.findByNationalCode(nationalCode).orElse(new PersonNullClass());
    }

    @Override
    public void deletePersonByNationalCode(String nationalCode) {
        repository.deleteByNationalCode(nationalCode);
    }

    @Override
    public Person updatePersonByNationalCode(Person person, String nationalCode) {
        Person findPerson = findPersonByNationalCode(nationalCode);
        return ! (findPerson instanceof PersonNullClass) ? repository.save(
                Person.builder().
                        firstName(CarAApplication.toStandard(person.getFirstName())).
                        lastName(CarAApplication.toStandard(person.getLastName())).
                        nationalCode(nationalCode).
                        dob(person.getDob()).
                        build()
        ):new PersonNullClass();
    }

    @Override
    public List<Person> personList(){
        return repository.findAll();
    }

}
