package com.tmkproperties.mail_server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("roomType")
    private String roomType;

    @JsonProperty("roomNumber")
    private Integer roomNumber;

    @JsonProperty("hotelType")
    private String hotelType;

    @JsonProperty("hotelName")
    private String hotelName;

    @JsonProperty("hotelLocation")
    private String hotelLocation;

    @JsonProperty("hotelDescription")
    private String hotelDescription;

    @JsonProperty("hotelContactPhone")
    private String hotelContactPhone;

    @JsonProperty("hotelContactEmail")
    private String hotelContactEmail;

    @JsonProperty("guestName")
    private String guestName;

    @JsonProperty("guestContactPhone")
    private String guestContactPhone;

    @JsonProperty("guestContactEmail")
    private String guestContactEmail;

    @JsonProperty("checkIn")
    private LocalDate checkIn;

    @JsonProperty("checkOut")
    private LocalDate checkOut;

    @JsonProperty("status")
    private BookingStatus status;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

}