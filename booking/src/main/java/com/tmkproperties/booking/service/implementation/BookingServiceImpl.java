package com.tmkproperties.booking.service.implementation;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDto;
import com.tmkproperties.booking.entity.Booking;
import com.tmkproperties.booking.exception.BadRequestException;
import com.tmkproperties.booking.exception.ResourceNotFoundException;
import com.tmkproperties.booking.mapper.BookingMapper;
import com.tmkproperties.booking.repository.BookingRepository;
import com.tmkproperties.booking.service.IBookingService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository repository;
    @Override
    public void createBooking(BookingRequestDto bookingRequestDto) {

        if(!bookingRequestDto.getCheckOut().isAfter(bookingRequestDto.getCheckIn())){
            throw new RuntimeException("Check-out date must be after check-in date");
        }
        //        TODO:: Check Availability == hotel service
        //        TODO:: get price per night == hotel service

        double amount = 15000;
        Booking booking = BookingMapper.toBooking(bookingRequestDto);
        booking.setAmount(amount);

        System.out.println(booking.toString());
        repository.save(booking);

        //        TODO:: Update Unavailable dates == hotel service
    }

    @Override
    public BookingResponseDto findById(Long id) {
        Booking booking = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );
        return BookingMapper.toBookingResponseDto(booking);
    }

    @Override
    public List<BookingResponseDto> findAll() {
        List<Booking> bookings = repository.findAll();

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }
        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> findAllByStatus(String statusString) {
        BookingStatus.isValidStatus(statusString);

        List<Booking> bookings = repository.findAllByStatus(BookingStatus.valueOf(statusString.toUpperCase()));

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }
        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> findAllByRoomId(Long roomId) {
        List<Booking> bookings = repository.findAllByRoomId(roomId);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }
        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> findAllByUserId(Long userId) {
        List<Booking> bookings = repository.findAllByUserId(userId);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }
        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public void changeBookingDates(Long id, BookingRequestDto bookingRequestDto) {
        Booking booking = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        if(!bookingRequestDto.getCheckOut().isAfter(bookingRequestDto.getCheckIn())){
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        booking.setCheckIn(bookingRequestDto.getCheckIn());
        booking.setCheckOut(bookingRequestDto.getCheckOut());
        repository.save(booking);

    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = findBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        repository.save(booking);
    }

    @Override
    public void approveBooking(Long id) {
        Booking booking = findBookingById(id);
        booking.setStatus(BookingStatus.CONFIRMED);
        repository.save(booking);
    }

    @Override
    public void rejectBooking(Long id) {
        Booking booking = findBookingById(id);
        booking.setStatus(BookingStatus.REJECTED);
        repository.save(booking);
    }

    @Override
    public void checkInBooking(Long id) {
        Booking booking = findBookingById(id);
        booking.setStatus(BookingStatus.CHECKED_IN);
        repository.save(booking);
    }

    @Override
    public void checkOutBooking(Long id) {
        Booking booking = findBookingById(id);
        booking.setStatus(BookingStatus.CHECKED_OUT);
        repository.save(booking);
    }

    private Booking findBookingById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Booking not found with id: " + id)
        );
    }

}
