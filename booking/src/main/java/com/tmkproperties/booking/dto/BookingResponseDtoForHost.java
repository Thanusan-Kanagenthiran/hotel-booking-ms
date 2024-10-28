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
        name = "Booking Response for Host",
        description = "Schema for booking response for host"
)
public class BookingResponseDtoForHost {
    private Long id;
    private Long roomId;
    private String guestName;
    private String guestContactPhone;
    private String guestContactEmail;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BookingStatus status;
    private BigDecimal amount;
}
