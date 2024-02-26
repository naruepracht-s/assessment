package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {
    Lottery findByTicket(String ticket);

    @Query(value = "SELECT ticket FROM lottery.public.lottery", nativeQuery = true)
    List<String> findAllTickets();
}
