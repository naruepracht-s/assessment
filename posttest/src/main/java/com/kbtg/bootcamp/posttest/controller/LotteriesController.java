package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.entity.Lottery;
import com.kbtg.bootcamp.posttest.model.IdResponse;
import com.kbtg.bootcamp.posttest.model.TicketListResponse;
import com.kbtg.bootcamp.posttest.model.TicketResponse;
import com.kbtg.bootcamp.posttest.model.UserLotteriesDetailResponse;
import com.kbtg.bootcamp.posttest.service.LotteriesService;
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
        lotteriesService.createLotteries(request);
        return new ResponseEntity<>(new TicketResponse(request.getTicket()), HttpStatus.CREATED);
    }

    @PostMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<IdResponse> purchaseLottery(
            @PathVariable("userId") String userId,
            @PathVariable("ticketId") String ticketId) {
        IdResponse response = lotteriesService.purchaseLottery(userId, ticketId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/lotteries")
    public ResponseEntity<UserLotteriesDetailResponse> getUserLotteriesDetail(
            @PathVariable String userId) {
        return new ResponseEntity<>(lotteriesService.getUserLotteriesDetail(userId), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/lotteries/{ticketId}")
    public ResponseEntity<TicketResponse> refundLottery(
            @PathVariable("userId") String userId,
            @PathVariable("ticketId") String ticketId) {
        return new ResponseEntity<>(lotteriesService.refundLottery(userId, ticketId), HttpStatus.OK);

    }

}
