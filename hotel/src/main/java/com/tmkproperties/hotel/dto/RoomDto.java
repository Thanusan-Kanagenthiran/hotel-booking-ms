package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.RoomType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {

    @NotNull
    private RoomType roomType;

    @NotNull
    private Integer maximumNumberOfGuests;

    @NotNull
    private BigDecimal pricePerNight;

    @NotNull
    private Integer roomNo;
}
