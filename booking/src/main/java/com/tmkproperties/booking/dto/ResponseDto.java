package com.tmkproperties.booking.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data @AllArgsConstructor
@Schema(
    description = "Response for a successful operation",
    name = "Response",
    type = "object",
    example = "{ \"statusCode\": 200 ,\n" +
            "  \"statusMessage\": \"Lorem ipsum Success\" }"
)
public class ResponseDto {

    private HttpStatus statusCode;
    private  String statusMessage;

}