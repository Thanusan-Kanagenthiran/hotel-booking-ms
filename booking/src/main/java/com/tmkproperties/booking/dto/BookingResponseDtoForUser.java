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
        name = "Booking Response for User",
        description = "Booking Response for User"
)
public class BookingResponseDtoForUser {
    private Long id;
    private String guestName;
    private String guestContactPhone;
    private String guestContactEmail;
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
