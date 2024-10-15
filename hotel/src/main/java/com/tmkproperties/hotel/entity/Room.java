package com.tmkproperties.hotel.entity;

import com.tmkproperties.hotel.constants.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Hotel hotel;

}
