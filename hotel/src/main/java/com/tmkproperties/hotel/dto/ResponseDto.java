package com.tmkproperties.hotel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ResponseDto {

    private HttpStatus statusCode;
    private  String statusMessage;

}