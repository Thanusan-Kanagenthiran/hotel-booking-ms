package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.RoomType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {

    @NotNull(message = "Room number cannot be null")
    @Positive(message = "Room number must be positive")
    private Integer roomNumber;

    @NotNull(message = "Room type cannot be null")
    private RoomType roomType;

    @Positive(message = "Maximum number of guests must be positive")
    @NotNull(message = "Maximum number of guests cannot be null")
    private Integer maximumNumberOfGuests;

    @Positive(message = "Price per night must be positive")
    @NotNull(message = "Price per night cannot be null")
    private BigDecimal pricePerNight;

    @NotNull(message = "Hotel ID cannot be null")
    private Long hotelId;

}
