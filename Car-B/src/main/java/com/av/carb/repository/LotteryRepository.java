package com.av.carb.repository;

import com.av.carb.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery , Long> {
    Optional<Lottery> findByPersonNationalCode(String personNationalCode);
}
