package com.av.carb.controller;

import com.av.carb.model.Lottery;
import com.av.carb.model.LotteryNullClass;
import com.av.carb.request.LotteryRequest;
import com.av.carb.service.LotteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/carb/lottery")
@Slf4j
public class LotteryController {

    private final LotteryService service;

    @Autowired
    public LotteryController(LotteryService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Lottery> saveLottery(@RequestBody LotteryRequest request){
        Lottery lottery = service.saveLottery(request.personNationalCode() , request.carBuildNumber());
        if (lottery instanceof LotteryNullClass){
            return new ResponseEntity<>(new LotteryNullClass() , HttpStatus.NOT_ACCEPTABLE);
        }else {
            log.info("New lottery created..");
            return new ResponseEntity<>(lottery , HttpStatus.CREATED);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Lottery>> findAllLotteries(){
        log.info("List of lotteries : ");
        return new ResponseEntity<>(service.getLotteries() , HttpStatus.FOUND);
    }

    @GetMapping("/{personNationalCode}")
    public ResponseEntity<Lottery> findLotteryByPersonNationalCode(
            @PathVariable("personNationalCode") String personNationalCode){
        Lottery lottery = service.findLotteryByPersonNationalCode(personNationalCode);
        if(lottery instanceof LotteryNullClass){
            log.info("The lottery with person nationalCode : '{}' not found !!! : " , personNationalCode);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            log.info("The lottery with person nationalCode : '{}' founded : " , lottery.getPerson().getNationalCode());
            return new ResponseEntity<>(lottery , HttpStatus.FOUND);
        }
    }
}