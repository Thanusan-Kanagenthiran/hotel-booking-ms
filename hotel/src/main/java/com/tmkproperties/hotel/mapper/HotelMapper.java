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
                .hotelName(hotelRequestDto.getHotelName())
                .hotelDescription(hotelRequestDto.getHotelDescription())
                .hotelLocation(hotelRequestDto.getHotelLocation())
                .hotelContactEmail(hotelRequestDto.getHotelContactEmail())
                .hotelContactPhone(hotelRequestDto.getHotelContactPhone())
                .hotelType(hotelRequestDto.getHotelType())
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
                .hotelName(hotel.getHotelName())
                .hotelLocation(hotel.getHotelLocation())
                .hotelDescription(hotel.getHotelDescription())
                .hotelContactEmail(hotel.getHotelContactEmail())
                .hotelContactPhone(hotel.getHotelContactPhone())
                .rooms(roomResponseDtos)
                .build();
    }

}
