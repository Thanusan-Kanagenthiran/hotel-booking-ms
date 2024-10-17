package com.tmkproperties.booking.dto;


import com.tmkproperties.booking.constants.RoomType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomResponseDto {
    private Long id;
    private RoomType roomType;
    private Integer roomNumber;
    private Integer maximumNumberOfGuests;
    @NotNull
    private BigDecimal pricePerNight;

}
