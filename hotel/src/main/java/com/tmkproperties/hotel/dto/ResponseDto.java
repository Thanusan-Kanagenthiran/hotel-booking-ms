package com.tmkproperties.hotel.dto;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@ApiResponse(
        responseCode = "200",
        description = "HTTP status code OK - OK",
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
)
public class ResponseDto {

    private HttpStatus statusCode;
    private  String statusMessage;

}