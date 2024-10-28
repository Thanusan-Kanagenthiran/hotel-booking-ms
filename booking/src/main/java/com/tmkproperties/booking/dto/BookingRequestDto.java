package com.tmkproperties.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
        example = "{ \n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"email\": \"example@gamil.com\",\n" +
                "  \"mobile\": \"0772433010\",\n" +
                "  \"roomId\": 1,\n" +
                "  \"checkIn\": \"2022-01-01\",\n" +
                "  \"checkOut\": \"2022-01-02\"\n" +
                "}"
)
public class BookingRequestDto {

    @Schema(description = "User name", example = "John Doe")
    @NotNull(message = "User name cannot be null")
    @Size(min = 2, max = 50, message = "User name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "User name must only contain letters and spaces")
    private String name;

    @Schema(description = "User id", example = "example@gamil.com")
    @NotNull(message = "User id cannot be null")
    @Email(message = "Invalid email format")
    private String email;


    @Schema(description = "Mobile number",
            example = "0772433010, +94772433010, 0712345678")
    @NotNull(message = "Mobile number cannot be null")
    @Pattern(regexp = "^(\\+94|0)?[1-9]\\d{8}$", message = "Invalid mobile number format")
    private String mobile;

    @Schema(description = "Room id", example = "1")
    @NotNull(message = "Room id cannot be null")
    @Positive(message = "Room id cannot be negative")
    private Long roomId;

    @Schema(description = "Check in date", example = "2022-01-01")
    @NotNull(message = "Check in date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkIn;

    @Schema(description = "Check out date", example = "2022-01-02")
    @NotNull(message = "Check out date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOut;

}
