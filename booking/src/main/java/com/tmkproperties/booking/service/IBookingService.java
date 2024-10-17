package com.tmkproperties.booking.service;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDto;
import jakarta.validation.Valid;

import java.util.List;


public interface IBookingService {
    void createBooking(@Valid BookingRequestDto bookingRequestDto);

    BookingResponseDto findById(Long id);

    List<BookingResponseDto> findAll();
    List<BookingResponseDto> findAllByStatus( String statusString);
    List<BookingResponseDto> findAllByRoomId(Long roomId);
    List<BookingResponseDto> findAllByUserId(Long userId);

    void changeBookingDates(Long id, BookingRequestDto bookingRequestDto);

    void cancelBooking(Long id);

    void approveBooking(Long id);

    void rejectBooking(Long id);

    void checkInBooking(Long id);

    void checkOutBooking(Long id);
}
