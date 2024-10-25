package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {

    @NotNull(message = "Room number cannot be null")
    @Positive(message = "Room number must be positive")
    private Integer roomNumber;

    @NotNull(message = "Room type cannot be null")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Positive(message = "Maximum number of guests must be positive")
    @NotNull(message = "Maximum number of guests cannot be null")
    @Min(value = 1, message = "Maximum number of guests must be at least 1")
    private Integer maximumNumberOfGuests;

    @Positive(message = "Price per night must be positive")
    @NotNull(message = "Price per night cannot be null")
    @Min(value = 500, message = "Price per night must be at least 500")
    private BigDecimal pricePerNight;

    @NotNull(message = "Hotel ID cannot be null")
    @Positive(message = "Hotel ID must be positive")
    private Long hotelId;

}
