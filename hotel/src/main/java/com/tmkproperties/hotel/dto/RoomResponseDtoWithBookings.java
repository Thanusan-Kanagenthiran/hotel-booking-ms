package com.tmkproperties.hotel.dto;

import com.tmkproperties.hotel.constants.HotelType;
import com.tmkproperties.hotel.constants.RoomType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class RoomResponseDtoWithBookings {
    private Long id;
    private RoomType roomType;
    private Integer roomNumber;
    private Integer maximumNumberOfGuests;
    private BigDecimal pricePerNight;
    private HotelType hotelType;
    private  List<BookingResponseDto> bookings;
}
