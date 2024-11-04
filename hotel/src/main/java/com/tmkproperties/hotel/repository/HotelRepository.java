package com.tmkproperties.hotel.repository;

import com.tmkproperties.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByHotelName(String name);

    List<Hotel> findByHotelContactEmail(String email);

    Hotel findByIdAndHotelContactEmail(Long id, String email);
}
