package com.av.cara.controller;

import com.av.cara.model.PersonNullClass;
import com.av.cara.service.PersonService;
import com.av.cara.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cara/person")
@Slf4j
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }


    @PostMapping("/save")
    public ResponseEntity<Person> savePerson(@RequestBody Person person){
        Person savePerson = service.savePerson(person);
        if (savePerson instanceof PersonNullClass){
            log.info("We found a person with this national code !!!");
            return new ResponseEntity<>(new PersonNullClass() , HttpStatus.NOT_ACCEPTABLE);
        }else {
            log.info("New person created..");
            return new ResponseEntity<>(savePerson , HttpStatus.CREATED);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Person>> findAllPersons(){
        return new ResponseEntity<>(service.personList() , HttpStatus.FOUND);
    }

    @GetMapping("/{nationalCode}")
    public ResponseEntity<Person> findPersonByNationalCode(@PathVariable("nationalCode") String nationalCode){
        Person person = service.findPersonByNationalCode(nationalCode);
        if(person instanceof PersonNullClass){
            log.info("The person with national code : '{}' not found !!!" , nationalCode);
            return new ResponseEntity<>(new PersonNullClass() , HttpStatus.NOT_FOUND);
        }else{
            log.info("The person with national code : '{}' founded : " , nationalCode);
            return new ResponseEntity<>(person, HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/{nationalCode}")
    public ResponseEntity<Person> deletePersonByNationalCode(@PathVariable("nationalCode") String nationalCode){
        service.deletePersonByNationalCode(nationalCode);
        log.info("The person with national code : '{} deleted !!!" , nationalCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{nationalCode}")
    public ResponseEntity<Person> updatePersonByNationalCode(@PathVariable("nationalCode") String nationalCode,
                                                      @RequestBody Person person){
        Person updatePersonByNationalCode = service.updatePersonByNationalCode(person, nationalCode);
        if(updatePersonByNationalCode instanceof PersonNullClass){
            log.info("The person with national code : '{}' not found !!!" , nationalCode);
            return new ResponseEntity<>(new PersonNullClass(), HttpStatus.NOT_FOUND);
        }else{
            log.info("The person with national code : '{}' upgraded !!! : " , nationalCode);
            return new ResponseEntity<>(updatePersonByNationalCode, HttpStatus.UPGRADE_REQUIRED);
        }
    }
}
