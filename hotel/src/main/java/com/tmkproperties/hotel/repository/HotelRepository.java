package com.tmkproperties.hotel.repository;

import com.tmkproperties.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByNameOrPhoneOrEmail(String name,String phone, String email);
}
