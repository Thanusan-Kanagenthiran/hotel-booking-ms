package com.tmkproperties.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "Booking request",
        description = "Schema for booking request",
        example = "{\n" +
        "    \"userId\": 1,\n" +
        "    \"roomId\": 1,\n" +
        "    \"checkIn\": \"2022-01-01\",\n" +
        "    \"checkOut\": \"2022-01-02\"\n" +
        "}"
)
public class BookingRequestDto {
    @Schema(description = "User id", example = "1", required = true)
    @NotNull(message = "User id cannot be null")
    @Positive(message = "User id cannot be negative")
    private Long userId;

    @Schema(description = "Room id", example = "1", required = true)
    @NotNull(message = "Room id cannot be null")
    @Positive(message = "Room id cannot be negative")
    private Long roomId;

    @Schema(description = "Check in date", example = "2022-01-01", required = true)
    @NotNull(message = "Check in date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkIn;

    @Schema(description = "Check out date", example = "2022-01-02", required = true)
    @NotNull(message = "Check out date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOut;

}
