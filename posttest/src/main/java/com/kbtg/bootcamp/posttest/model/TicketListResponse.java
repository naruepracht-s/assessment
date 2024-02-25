package com.kbtg.bootcamp.posttest.model;

import lombok.Data;

import java.util.List;

@Data
public class TicketListResponse {
    private List<String> tickets;
}
