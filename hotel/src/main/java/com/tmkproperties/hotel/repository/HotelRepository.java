package com.tmkproperties.hotel.repository;

import com.tmkproperties.hotel.entity.Hotel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Hotel> findBySlug(String slug);

}
