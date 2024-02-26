package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.IdResponse;
import com.kbtg.bootcamp.posttest.model.TicketListResponse;
import com.kbtg.bootcamp.posttest.model.TicketResponse;
import com.kbtg.bootcamp.posttest.model.UserLotteriesDetailResponse;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LotteriesService {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private UserTicketRepository userTicketRepository;

    public TicketListResponse getLotteries() {
        TicketListResponse response = new TicketListResponse();
        List<String> ticketList =  lotteryRepository.findAllTickets();
        response.setTickets(ticketList);
        return response;
    }

    public void createLottery(Lottery lottery) {
        lotteryRepository.save(lottery);
    }

    @Transactional
    public IdResponse purchaseLottery(String userId, String ticketId) {
        Lottery lottery = lotteryRepository.findByTicket(ticketId);
        if (lottery != null) {
            if (lottery.getAmount() != 0) {

                lottery.setAmount(lottery.getAmount()-1);
                UserTicket userTicket = new UserTicket();
                userTicket.setUserId(userId);
                userTicket.setLottery(lottery);
                userTicket.setPurchaseDate(new Date());
                return new IdResponse(userTicketRepository.save(userTicket).getId());
            } else {
                throw new BadRequestException("Lottery ticket number "+ticketId+" is sold out");
            }
        } else {
            throw new NotFoundException("Lottery ticket number "+ticketId+" not found");
        }
    }

    @Transactional
    public TicketResponse refundLottery(String userId, String ticketId) {
        UserTicket userTicket = userTicketRepository.findByUserIdAndTicket(userId, ticketId);
        if (userTicket != null) {
            userTicket.getLottery().setAmount(userTicket.getLottery().getAmount()+1);
            lotteryRepository.save(userTicket.getLottery());
            userTicketRepository.delete(userTicket);
            return new TicketResponse(ticketId);
        } else {
            throw new BadRequestException("Purchase records for ticket number "+ticketId+" not found");
        }
    }

    public UserLotteriesDetailResponse getUserLotteriesDetail(String userId) {
        List<UserTicket> userTickets = userTicketRepository.findByUserId(userId);
        UserLotteriesDetailResponse response = new UserLotteriesDetailResponse();
        if (!userTickets.isEmpty()) {
            response.setCount(userTickets.size());
            response.setCost(userTickets.stream().mapToInt(o -> o.getLottery().getPrice()).sum());
            response.setTickets(new ArrayList<>());
            for (UserTicket object: userTickets) {
                response.getTickets().add(object.getLottery().getTicket());
            }
        }
        return response;
    }
}
