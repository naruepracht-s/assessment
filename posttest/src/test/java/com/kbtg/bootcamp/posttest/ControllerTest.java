package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.controller.LotteriesController;
import com.kbtg.bootcamp.posttest.repository.LotteryRepository;
import com.kbtg.bootcamp.posttest.security.SecurityConfig;
import com.kbtg.bootcamp.posttest.service.LotteriesService;
import jakarta.servlet.http.Cookie;
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

    @Test
    public void getLotteries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lotteries"))
                .andExpect(status().isOk());
    }

    @Test
    public void createLottery() throws Exception {
        String requestBody = "{\"ticket\": \"123354\", \"price\": 80, \"amount\": 1}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/lotteries")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:password".getBytes())))
                .andExpect(status().isCreated());
    }

}
