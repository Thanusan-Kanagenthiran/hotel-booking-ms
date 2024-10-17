package com.tmkproperties.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "User id cannot be null")
    @Positive(message = "User id cannot be negative")
    private Long userId;

    @NotNull(message = "Room id cannot be null")
    @Positive(message = "Room id cannot be negative")
    private Long roomId;

    @NotNull(message = "Check in date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkIn;

    @NotNull(message = "Check out date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOut;

}
