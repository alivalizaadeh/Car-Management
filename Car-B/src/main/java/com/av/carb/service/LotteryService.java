package com.av.carb.service;

import com.av.carb.model.Lottery;

import java.util.List;

public interface LotteryService {
    Lottery saveLottery(String personNationalCode , String carBuildNumber);
    Lottery findLotteryByPersonNationalCode(String personNationalCode);
    List<Lottery> getLotteries();
    boolean isPersonRegisteredBefore(String personNationalCode);
}
