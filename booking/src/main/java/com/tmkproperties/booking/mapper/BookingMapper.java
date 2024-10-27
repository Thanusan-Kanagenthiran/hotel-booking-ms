package com.tmkproperties.booking.mapper;

import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDtoForHost;
import com.tmkproperties.booking.dto.BookingResponseDtoForUser;
import com.tmkproperties.booking.dto.RoomResponseDtoWithDetails;
import com.tmkproperties.booking.entity.Booking;

public class BookingMapper {

    public static Booking toBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .guestName(bookingRequestDto.getName())
                .guestEmail(bookingRequestDto.getEmail())
                .guestPhone(bookingRequestDto.getMobile())
                .roomId(bookingRequestDto.getRoomId())
                .checkIn(bookingRequestDto.getCheckIn())
                .checkOut(bookingRequestDto.getCheckOut())
                .build();
    }

    public static BookingResponseDtoForHost toBookingResponseDtoForHost(Booking booking, Long roomID) {
        return BookingResponseDtoForHost.builder()
                .id(booking.getId())
                .guestName(booking.getGuestName())
                .guestContactPhone(booking.getGuestPhone())
                .guestContactEmail(booking.getGuestEmail())
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
                .roomType(roomDetails.getRoomType())
                .roomNumber(roomDetails.getRoomNumber())
                .hotelType(roomDetails.getHotelType())
                .guestName(booking.getGuestName())
                .guestContactPhone(booking.getGuestPhone())
                .guestContactEmail(booking.getGuestEmail())
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
