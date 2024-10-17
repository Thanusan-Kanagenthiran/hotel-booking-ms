package com.tmkproperties.booking.dto;

import com.tmkproperties.booking.constants.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(
        name = "Booking Response",
        description = "Schema for booking response",
        example = """
                {
                  "id": 1,
                  "userId": 1,
                  "roomId": 1,
                  "checkIn": "2022-01-01",
                  "checkOut": "2022-01-02",
                  "status": "PENDING",
                  "amount": 100.0
                }"""
)
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private BigDecimal amount;
}
