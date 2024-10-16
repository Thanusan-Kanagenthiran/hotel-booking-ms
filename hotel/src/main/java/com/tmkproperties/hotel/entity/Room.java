package com.tmkproperties.hotel.entity;

import com.tmkproperties.hotel.constants.RoomType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Integer maximumNumberOfGuests;
    private BigDecimal pricePerNight;

    @ElementCollection
    @Nullable
    private List<LocalDate> unavailableDates = new ArrayList<>();

    private Integer roomNumber;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @PrePersist
    @PreUpdate
    public void removePastUnavailableDates() {
        LocalDate today = LocalDate.now();
        if (this.unavailableDates != null) {
            this.unavailableDates = this.unavailableDates.stream()
                    .filter(date -> !date.isBefore(today))
                    .collect(Collectors.toList());
        }
    }


}
