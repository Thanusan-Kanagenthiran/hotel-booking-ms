package com.tmkproperties.booking.dto;

import com.tmkproperties.booking.constants.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(
        name = "Booking Response",
        description = "Schema for booking response",
        example = "{\n" +
                "  \"id\": 1,\n" +
                "  \"userId\": 1,\n" +
                "  \"roomId\": 1,\n" +
                "  \"checkIn\": \"2022-01-01\",\n" +
                "  \"checkOut\": \"2022-01-02\",\n" +
                "  \"status\": \"PENDING\",\n" +
                "  \"amount\": 100.0\n" +
                "}"
)
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private double amount;
}
