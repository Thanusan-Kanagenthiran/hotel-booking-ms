package com.tmkproperties.booking.service;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDto;
import com.tmkproperties.booking.dto.UpdateBookingDatesDto;
import jakarta.validation.Valid;

import java.util.List;


public interface IBookingService {
    void createBooking(@Valid BookingRequestDto bookingRequestDto);

    BookingResponseDto findById(Long id);

    void changeBookingDates(Long id, UpdateBookingDatesDto updateBookingDatesDto);

    void cancelBooking(Long id);

    void approveBooking(Long id);

    void rejectBooking(Long id);

    void checkInBooking(Long id);

    void checkOutBooking(Long id);

    List<BookingResponseDto> findBookings(BookingStatus status, Long roomId, Long userId);
}
