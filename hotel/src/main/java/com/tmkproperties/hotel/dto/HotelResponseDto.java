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
    private String name;
    private String location;
    private String description;
    private String phone;
    private String email;
    private List<RoomResponseDto> rooms;
}
