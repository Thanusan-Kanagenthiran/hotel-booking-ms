package com.tmkproperties.booking.service;
import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDtoForHost;
import com.tmkproperties.booking.dto.BookingResponseDtoForUser;
import com.tmkproperties.booking.dto.UpdateBookingDatesDto;
import com.tmkproperties.booking.entity.Booking;
import jakarta.validation.Valid;

import java.util.List;


public interface IBookingService {
    BookingResponseDtoForUser createBooking(@Valid BookingRequestDto bookingRequestDto, String email);

    void changeBookingDates(Long id, UpdateBookingDatesDto updateBookingDatesDto, String email);

    void cancelBooking(Long id, String email);

    BookingResponseDtoForUser approveBooking(Long id, String email);

    void rejectBooking(Long id, String email);

    void checkInBooking(Long id, String email);

    void checkOutBooking(Long id, String email);

    List<BookingResponseDtoForUser> getAllBookingsByUser(String email);

    BookingResponseDtoForUser getUserBooking(Long id, String email);

    List<BookingResponseDtoForHost> getAllHostBookings(String email);

    BookingResponseDtoForHost getHostBooking(Long id, String email);
}
