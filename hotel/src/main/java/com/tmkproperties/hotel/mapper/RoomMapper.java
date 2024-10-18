package com.tmkproperties.hotel.mapper;

import com.tmkproperties.hotel.dto.BookingResponseDto;
import com.tmkproperties.hotel.dto.RoomRequestDto;
import com.tmkproperties.hotel.dto.RoomResponseDto;
import com.tmkproperties.hotel.entity.Hotel;
import com.tmkproperties.hotel.entity.Room;

import java.util.List;

public class RoomMapper {

    public static Room toRoom(RoomRequestDto roomRequestDto) {
        return Room.builder()
                .roomType(roomRequestDto.getRoomType())
                .roomNumber(roomRequestDto.getRoomNumber())
                .maximumNumberOfGuests(roomRequestDto.getMaximumNumberOfGuests())
                .pricePerNight(roomRequestDto.getPricePerNight())
                .hotel(Hotel.builder().id(roomRequestDto.getHotelId()).build())
                .build();
    }


    public static RoomResponseDto toRoomResponseDtoWithBookings(Room room, List<BookingResponseDto> bookings) {

        return RoomResponseDto.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .maximumNumberOfGuests(room.getMaximumNumberOfGuests())
                .pricePerNight(room.getPricePerNight())
                .bookings(bookings)
                .build();
    }

    public static RoomResponseDto toRoomResponseDto(Room room) {

        return RoomResponseDto.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .maximumNumberOfGuests(room.getMaximumNumberOfGuests())
                .pricePerNight(room.getPricePerNight())
                .build();
    }

}
