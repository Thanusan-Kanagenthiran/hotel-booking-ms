package com.tmkproperties.booking.repository;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByRoomId(Long roomId);
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatusAndRoomId(BookingStatus status, Long roomId);
    List<Booking> findByStatusAndUserId(BookingStatus status, Long userId);
    List<Booking> findByRoomIdAndUserId(Long roomId, Long userId);
    List<Booking> findByStatusAndRoomIdAndUserId(BookingStatus status, Long roomId, Long userId);
}

