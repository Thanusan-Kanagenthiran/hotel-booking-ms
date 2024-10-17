package com.tmkproperties.booking.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Response for invalid request",
        type = "object",
        example = """
                {
                  "apiPath": "/booking",
                  "errorCode":40# \
                  "errorMessage": "Lorem ipsum dolor sit amet",
                  "errorTime": "2022-01-01T00:00:00"
                }"""
)
public class ErrorResponseDto {

    private  String apiPath;
    private HttpStatus errorCode;
    private  String errorMessage;
    private LocalDateTime errorTime;


}