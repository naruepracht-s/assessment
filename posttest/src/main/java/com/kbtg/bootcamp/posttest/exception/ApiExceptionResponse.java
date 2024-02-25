package com.kbtg.bootcamp.posttest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {
    private int status;
    private List<String> message;
    private Date timestamp;

}
