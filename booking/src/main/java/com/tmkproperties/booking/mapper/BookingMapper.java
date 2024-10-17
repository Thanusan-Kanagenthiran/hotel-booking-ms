package com.tmkproperties.booking.mapper;

import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDto;
import com.tmkproperties.booking.entity.Booking;

public class BookingMapper {

    public static Booking toBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .userId(bookingRequestDto.getUserId())
                .roomId(bookingRequestDto.getRoomId())
                .checkIn(bookingRequestDto.getCheckIn())
                .checkOut(bookingRequestDto.getCheckOut())
                .build();
    }

    public static BookingResponseDto toBookingResponseDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .roomId(booking.getRoomId())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .status(booking.getStatus())
                .amount(booking.getAmount())
                .build();
    }

}
