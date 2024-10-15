package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.RoomType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private RoomType roomType;
    private int maximumNumberOfGuests;
    private BigDecimal pricePerNight;


}