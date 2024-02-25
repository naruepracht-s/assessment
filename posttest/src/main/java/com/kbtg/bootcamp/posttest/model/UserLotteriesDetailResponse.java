package com.kbtg.bootcamp.posttest.model;

import lombok.Data;

import java.util.List;

@Data
public class UserLotteriesDetailResponse {
    private List<String> tickets;
    private Integer count;
    private Integer cost;
}
