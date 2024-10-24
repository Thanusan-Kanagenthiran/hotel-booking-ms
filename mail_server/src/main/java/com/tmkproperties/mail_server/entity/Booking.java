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

    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("userPhone")
    private String userPhone;

    @JsonProperty("roomId")
    private Long roomId;

    @JsonProperty("checkIn")
    private LocalDate checkIn;

    @JsonProperty("checkOut")
    private LocalDate checkOut;

    @JsonProperty("status")
    private BookingStatus status;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("hotelEmail")
    private String hotelEmail;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdDate")
    private LocalDateTime createdDate;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonProperty("updatedDate")
    private LocalDateTime updatedDate;

}