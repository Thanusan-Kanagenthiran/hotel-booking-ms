package com.tmkproperties.hotel.entity;

import com.tmkproperties.hotel.constants.HotelType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private HotelType hotelType;

    @Column(unique = true, nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String hotelLocation;

    @Column(nullable = false)
    private String hotelDescription;

    @Column(nullable = false)
    private String hotelContactPhone;

    @Column(nullable = false)
    private String hotelContactEmail;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    private List<Room> rooms;

}
