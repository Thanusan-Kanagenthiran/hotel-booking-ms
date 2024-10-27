package com.tmkproperties.booking.repository;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByGuestEmail(String email);

    List<Booking> findByHotelEmail(String email);

    Optional<Booking> findByIdAndGuestEmail(Long id, String email);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :status WHERE b.id = :id")
    void updateBookingStatus(@Param("id") Long id, @Param("status") BookingStatus status);

    List<Booking> findByRoomId(Long roomId);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.checkIn = :checkIn, b.checkOut = :checkOut, b.amount = :amount WHERE b.id = :id")
    void updateBookingDates(@Param("id") Long id,
                            @Param("checkIn") LocalDate checkIn,
                            @Param("checkOut") LocalDate checkOut,
                            @Param("amount") BigDecimal amount);


    Optional<Booking> findByIdAndHotelEmail(Long id, String email);
}
