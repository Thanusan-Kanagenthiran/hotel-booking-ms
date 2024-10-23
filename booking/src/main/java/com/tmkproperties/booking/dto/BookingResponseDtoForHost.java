package com.tmkproperties.booking.dto;

import com.tmkproperties.booking.constants.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BookingResponseDtoForHost {
    private Long id;
    private Long roomId;
    private String guestContactPhone;
    private String guestContactEmail;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private BigDecimal amount;
}
