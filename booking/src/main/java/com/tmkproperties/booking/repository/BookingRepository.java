package com.tmkproperties.booking.repository;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByStatus(BookingStatus status);

    List<Booking> findAllByRoomId(Long roomId);

    List<Booking> findAllByUserId(Long userId);
}
