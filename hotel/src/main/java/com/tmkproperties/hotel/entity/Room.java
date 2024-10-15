package com.tmkproperties.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmkproperties.hotel.constants.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long roomId;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int maximumNumberOfGuests;

    @Column(precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;

}
