package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.model.IdResponse;
import com.kbtg.bootcamp.posttest.model.TicketListResponse;
import com.kbtg.bootcamp.posttest.model.TicketResponse;
import com.kbtg.bootcamp.posttest.model.UserLotteriesDetailResponse;
import com.kbtg.bootcamp.posttest.service.LotteriesService;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LotteriesController {

    @Autowired
    private LotteriesService lotteriesService;

    @GetMapping("/lotteries")
    public ResponseEntity<TicketListResponse> getLotteries() {
        return ResponseEntity.ok(lotteriesService.getLotteries());
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<TicketResponse> createLottery(
            @Validated @RequestBody Lottery request) {
        lotteriesService.createLottery(request);
        return new ResponseEntity<>(new TicketResponse(request.getTicket()), HttpStatus.CREATED);
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<IdResponse> purchaseLottery(
            @PathVariable("userId")
            @Pattern(regexp = "\\d{10}")
            @Size(min = 10, max = 10)
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "[0-9]+")
            @Size(min = 6, max = 6)
            String ticketId) {
        IdResponse response = lotteriesService.purchaseLottery(userId, ticketId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<UserLotteriesDetailResponse> getUserLotteriesDetail(
            @PathVariable
            @Pattern(regexp = "\\d{10}")
            @Size(min = 10, max = 10)
            String userId) {
        return new ResponseEntity<>(lotteriesService.getUserLotteriesDetail(userId), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<TicketResponse> refundLottery(
            @PathVariable("userId")
            @Pattern(regexp = "\\d{10}") @Size(min = 10, max = 10)
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "[0-9]+") @Size(min = 6, max = 6)
            String ticketId) {
        return new ResponseEntity<>(lotteriesService.refundLottery(userId, ticketId), HttpStatus.OK);
    }
}
