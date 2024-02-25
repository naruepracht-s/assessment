package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Integer> {
    List<UserTicket> findByUserId(String userId);

    @Query(value = "SELECT ut.* FROM user_ticket ut JOIN lottery l ON ut.lottery_id = l.id WHERE ut.user_id = :userId AND l.ticket = :ticket", nativeQuery = true)
    UserTicket findByUserIdAndTicket(@Param("userId") String userId, @Param("ticket") String ticket);

}
