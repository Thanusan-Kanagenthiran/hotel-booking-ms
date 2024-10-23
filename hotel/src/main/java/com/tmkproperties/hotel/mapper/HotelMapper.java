package com.tmkproperties.hotel.mapper;

import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.dto.RoomResponseDto;
import com.tmkproperties.hotel.entity.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelMapper {

    public static Hotel toHotel(HotelRequestDto hotelRequestDto) {
        return Hotel.builder()
                .name(hotelRequestDto.getName())
                .location(hotelRequestDto.getLocation())
                .description(hotelRequestDto.getDescription())
                .hotelType(hotelRequestDto.getHotelType())
                .email(hotelRequestDto.getEmail())
                .phone(hotelRequestDto.getPhone())
                .build();
    }

    public static HotelResponseDto toHotelResponseDto(Hotel hotel) {
        List<RoomResponseDto> roomResponseDtos = hotel.getRooms()
                .stream()
                .map(RoomMapper::toRoomResponseDto)
                .collect(Collectors.toList());

        return HotelResponseDto.builder()
                .id(hotel.getId())
                .hotelType(hotel.getHotelType())
                .name(hotel.getName())
                .location(hotel.getLocation())
                .description(hotel.getDescription())
                .phone(hotel.getPhone())
                .email(hotel.getEmail())
                .rooms(roomResponseDtos)
                .build();
    }

}
