package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private BigDecimal amount;
}
