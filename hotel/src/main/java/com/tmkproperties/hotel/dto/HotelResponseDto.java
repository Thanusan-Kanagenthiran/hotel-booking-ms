package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.HotelType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HotelResponseDto {
    private Long id;
    private HotelType hotelType;
    private String hotelName;
    private String hotelLocation;
    private String hotelDescription;
    private String hotelContactPhone;
    private String hotelContactEmail;
    private List<RoomResponseDto> rooms;
}
