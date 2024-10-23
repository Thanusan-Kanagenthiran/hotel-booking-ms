package com.tmkproperties.booking.dto;

import com.tmkproperties.booking.constants.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BookingResponseDtoForUser {
    private Long id;
    private String roomType;
    private Integer roomNumber;
    private String hotelType;
    private String hotelName;
    private String hotelLocation;
    private String hotelDescription;
    private String hotelContactPhone;
    private String hotelContactEmail;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private BigDecimal amount;
}
