package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.controller.LotteriesController;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.security.SecurityConfig;
import com.kbtg.bootcamp.posttest.service.LotteriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LotteriesController.class)
@Import(SecurityConfig.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteriesService lotteriesService;

    @MockBean
    private LotteryRepository lotteryRepository;

    private String userId;

    private String ticketId;

    @BeforeEach
    void setUp() {
        userId = "0000000001";
        ticketId = "123456";
    }

    @Test
    public void getLotteries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lotteries"))
                .andExpect(status().isOk());
    }

    @Test
    public void createLottery() throws Exception {
        String requestBody = "{\"ticket\": \"123354\", \"price\": 80, \"amount\": 1}";
        mockMvc.perform(post("/admin/lotteries")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:password".getBytes())))
                .andExpect(status().isCreated());
    }

    @Test
    public void purchaseLottery() throws Exception {
        mockMvc.perform(post("/users/{userId}/lotteries/{ticketId}",
                        userId,
                        ticketId))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserLotteriesDetail() throws Exception {
        mockMvc.perform(get("/users/{userId}/lotteries",
                        userId))
                .andExpect(status().isOk());
    }

    @Test
    public void refundLottery() throws Exception {
        mockMvc.perform(delete("/users/{userId}/lotteries/{ticketId}",
                        userId,
                        ticketId))
                .andExpect(status().isOk());
    }

}
