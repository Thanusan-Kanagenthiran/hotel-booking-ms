package com.tmkproperties.booking.dto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomResponseDtoWithDetails {
    private Long id;
    private String roomType;
    private Integer roomNumber;
    private Integer maximumNumberOfGuests;
    private BigDecimal pricePerNight;
    private String hotelType;
    private String hotelName;
    private String location;
    private String description;
    private String phone;
    private String email;
}

