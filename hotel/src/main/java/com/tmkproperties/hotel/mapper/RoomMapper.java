package com.tmkproperties.hotel.mapper;

import com.tmkproperties.hotel.dto.BookingResponseDto;
import com.tmkproperties.hotel.dto.RoomRequestDto;
import com.tmkproperties.hotel.dto.RoomResponseDto;
import com.tmkproperties.hotel.dto.RoomResponseDtoWithBookings;
import com.tmkproperties.hotel.dto.RoomResponseDtoWithDetails;
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


    public static RoomResponseDtoWithBookings toRoomResponseDtoWithBookings(Room room, List<BookingResponseDto> bookings) {
        return RoomResponseDtoWithBookings.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .maximumNumberOfGuests(room.getMaximumNumberOfGuests())
                .pricePerNight(room.getPricePerNight())
                .bookings(bookings)
                .build();
    }


    public static RoomResponseDtoWithDetails toRoomResponseDtoWithDetail(Room room) {
        return RoomResponseDtoWithDetails.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .maximumNumberOfGuests(room.getMaximumNumberOfGuests())
                .pricePerNight(room.getPricePerNight())
                .email(room.getHotel().getEmail())
                .phone(room.getHotel().getPhone())
                .hotelType(room.getHotel().getHotelType())
                .hotelName(room.getHotel().getName())
                .location(room.getHotel().getLocation())
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
