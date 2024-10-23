package com.tmkproperties.booking.mapper;

import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDtoForHost;
import com.tmkproperties.booking.dto.BookingResponseDtoForUser;
import com.tmkproperties.booking.dto.RoomResponseDtoWithDetails;
import com.tmkproperties.booking.entity.Booking;

public class BookingMapper {

    public static Booking toBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .userEmail(bookingRequestDto.getEmail())
                .userPhone(bookingRequestDto.getMobile())
                .roomId(bookingRequestDto.getRoomId())
                .checkIn(bookingRequestDto.getCheckIn())
                .checkOut(bookingRequestDto.getCheckOut())
                .build();
    }

    public static BookingResponseDtoForHost toBookingResponseDtoForHost(Booking booking, Long roomID) {
        return BookingResponseDtoForHost.builder()
                .id(booking.getId())
                .guestContactPhone(booking.getUserPhone())
                .guestContactEmail(booking.getUserEmail())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .status(booking.getStatus())
                .amount(booking.getAmount())
                .roomId(roomID)
                .build();
    }

    public static BookingResponseDtoForUser toBookingResponseDtoForUser(Booking booking, RoomResponseDtoWithDetails roomDetails) {
        return BookingResponseDtoForUser.builder()
                .id(booking.getId())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .status(booking.getStatus())
                .amount(booking.getAmount())
                .hotelDescription(roomDetails.getDescription())
                .hotelLocation(roomDetails.getLocation())
                .hotelName(roomDetails.getHotelName())
                .hotelContactEmail(roomDetails.getEmail())
                .hotelContactPhone(roomDetails.getPhone())
                .build();
    }

}
