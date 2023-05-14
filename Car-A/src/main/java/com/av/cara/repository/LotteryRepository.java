package com.av.cara.repository;

import com.av.cara.model.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LotteryRepository extends JpaRepository<Lottery , Long> {
    Optional<Lottery> findByPersonNationalCode(String personNationalCode);
}
