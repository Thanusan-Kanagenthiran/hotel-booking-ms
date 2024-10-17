package com.tmkproperties.booking.dto;

import com.tmkproperties.booking.constants.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDto {

    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private double amount;
}
