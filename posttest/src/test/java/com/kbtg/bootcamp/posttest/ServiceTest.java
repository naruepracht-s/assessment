package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.entity.UserTicket;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.IdResponse;
import com.kbtg.bootcamp.posttest.model.TicketListResponse;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketRepository;
import com.kbtg.bootcamp.posttest.service.LotteriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private LotteriesService lotteriesService;

    @Mock
    private LotteryRepository lotteryRepository;

    @Mock
    private UserTicketRepository userTicketRepository;

    private String userId;
    private String ticketId;
    private Lottery lottery;


    @BeforeEach
    void setUp() {
        userId = "0000000001";
        ticketId = "123456";
        lottery = new Lottery(1, "123456", 80, 5);
    }

    @Test
    @DisplayName("Method getLotteries should return all lottery tickets from the database.")
    void getLotteriesShouldReturnLotteryTicketList() {
        List<String> lotteryTicketList = new ArrayList<>(List.of("123456", "777777", "654321"));
        when(lotteryRepository.findAllTickets()).thenReturn(lotteryTicketList);
        TicketListResponse serviceResponse = lotteriesService.getLotteries();
        assertEquals(serviceResponse.getTickets(), lotteryTicketList);
    }

    @Test
    @DisplayName("Method getLotteries should return an empty value if there are no lottery tickets in the database.")
    void getLotteriesShouldReturnEmptyIfNoLotteryInDB() {
        List<String> lotteryTicketList = new ArrayList<>();
        when(lotteryRepository.findAllTickets()).thenReturn(lotteryTicketList);
        TicketListResponse serviceResponse = lotteriesService.getLotteries();
        assertTrue(serviceResponse.getTickets().isEmpty());
    }

    @Test
    void purchaseLotterySuccessShouldReturnUserTicketId() {
        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);
        when(lotteryRepository.findByTicket(ticketId)).thenReturn(lottery);
        when(userTicketRepository.save(ArgumentMatchers.any(UserTicket.class))).thenReturn(userTicket);
        IdResponse serviceResponse = lotteriesService.purchaseLottery(userId, ticketId);
        assertEquals(serviceResponse.getId(), userTicket.getId());
    }

    @Test
    void purchaseLotteryIfNoDataShouldReturnNotFoundException() {
        lottery = null;
        when(lotteryRepository.findByTicket(ticketId)).thenReturn(lottery);
        NotFoundException serviceResponse = assertThrows(NotFoundException.class,
                () -> lotteriesService.purchaseLottery(userId, ticketId));
        assertEquals(serviceResponse.getMessage(), "Lottery ticket number 123456 not found");
    }

    @Test
    void purchaseLotteryIfNoLotteryShouldReturnNotFoundException() {
        lottery.setAmount(0);
        when(lotteryRepository.findByTicket(ticketId)).thenReturn(lottery);
        BadRequestException serviceResponse = assertThrows(BadRequestException.class,
                () -> lotteriesService.purchaseLottery(userId, ticketId));
        assertEquals(serviceResponse.getMessage(), "Lottery ticket number 123456 is sold out");
    }

}
