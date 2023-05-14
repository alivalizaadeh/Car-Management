package com.av.cara.service;

import com.av.cara.model.*;
import com.av.cara.repository.LotteryRepository;
import com.av.cara.response.CarBCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class LotteryServiceImpl implements LotteryService{

    private final LotteryRepository repository;
    private final PersonService personService;
    private final CarService carService;
    private final RestTemplate restTemplate;

    @Autowired
    public LotteryServiceImpl(LotteryRepository repository,
                              PersonService personService,
                              CarService carService,
                              RestTemplate restTemplate) {
        this.repository = repository;
        this.personService = personService;
        this.carService = carService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Lottery saveLottery(String personNationalCode , String carBuildNumber) {
        Person person = personService.findPersonByNationalCode(personNationalCode);
        Car car = carService.findCarByBuildNumber(carBuildNumber);
        if(person instanceof PersonNullClass){
            log.info("Person not found !!!");
            return new LotteryNullClass();
        }
        if(car instanceof CarNullClass){
            log.info("Car not found !!!");
            return new LotteryNullClass();
        }
        Lottery lottery = Lottery.builder().
                person(person).
                car(car).
                isAccepted(false).
                registeredAt(LocalDate.now()).
                build();
        if(isPersonRegisteredBefore(personNationalCode)){
            log.info("This person registered before to lottery !!!");
            return new LotteryNullClass();
        }
        try {
            restTemplate.getForEntity(
                    "http://localhost:8080/carb/lottery/{nationalCode}",
                    CarBCheckResponse.class ,
                    personNationalCode
            );
            log.info("This person registered to lottery service B !!!");
            return new LotteryNullClass();
        }catch (HttpClientErrorException e){
            log.info("This person not registered before to lottery service B !!!");
            return repository.save(lottery);
        }
    }

    @Override
    public Lottery findLotteryByPersonNationalCode(String personNationalCode) {
        return repository.findByPersonNationalCode(personNationalCode).orElse(new LotteryNullClass());
    }

    @Override
    public List<Lottery> getLotteries() {
        return repository.findAll();
    }

    @Override
    public boolean isPersonRegisteredBefore(String personNationalCode) {
        return !(findLotteryByPersonNationalCode(personNationalCode) instanceof LotteryNullClass);
    }
}
