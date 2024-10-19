package com.tmkproperties.booking.service.implementation;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDto;
import com.tmkproperties.booking.dto.RoomResponseDto;
import com.tmkproperties.booking.dto.UpdateBookingDatesDto;
import com.tmkproperties.booking.entity.Booking;
import com.tmkproperties.booking.exception.BadRequestException;
import com.tmkproperties.booking.exception.ResourceNotFoundException;
import com.tmkproperties.booking.mapper.BookingMapper;
import com.tmkproperties.booking.repository.BookingRepository;
import com.tmkproperties.booking.service.IBookingService;

import com.tmkproperties.booking.service.client.RoomFiegnClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository repository;
    private final RoomFiegnClient roomFiegnClient;

    @Override
    public void createBooking(BookingRequestDto bookingRequestDto) {

        BigDecimal amount;
        int numberOfDays;

        if (bookingRequestDto.getCheckOut().isBefore(bookingRequestDto.getCheckIn())) {
            throw new BadRequestException("Check out date cannot be before check in date");
        } else {
            numberOfDays = (int) (bookingRequestDto.getCheckOut().toEpochDay() - bookingRequestDto.getCheckIn().toEpochDay());
        }

        List<Booking> existingBookings = repository.findByRoomId(bookingRequestDto.getRoomId());

        for (Booking booking : existingBookings) {
            if (!booking.getStatus().equals(BookingStatus.REJECTED)) {
                if (bookingRequestDto.getCheckOut().isAfter(booking.getCheckIn()) ||
                        bookingRequestDto.getCheckIn().isBefore(booking.getCheckOut())) {
                    throw new ResourceNotFoundException("Room is already booked for the selected dates");
                }
            }
        }

        RoomResponseDto roomResponse;
        try {

            ResponseEntity<RoomResponseDto> response = roomFiegnClient.findById(bookingRequestDto.getRoomId());

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BadRequestException("Failed to fetch room details. Status code: " + response.getStatusCode());
            }

            roomResponse = response.getBody();

            if (roomResponse == null) {
                throw new BadRequestException("Room response is empty for room ID: " + bookingRequestDto.getRoomId());
            }

        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Room with ID: " + bookingRequestDto.getRoomId() + " not found.");
        } catch (FeignException.BadRequest ex) {
            throw new BadRequestException("Bad request when fetching room details: " + ex.getMessage());
        } catch (FeignException ex) {
            throw new BadRequestException("Error occurred when calling room service: " + ex.getMessage());
        }
        amount = roomResponse.getPricePerNight().multiply(BigDecimal.valueOf(numberOfDays));

        Booking booking = BookingMapper.toBooking(bookingRequestDto);
        booking.setAmount(amount);
        repository.save(booking);
    }

    @Override
    public BookingResponseDto findById(Long id) {
        Booking booking = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );
        return BookingMapper.toBookingResponseDto(booking);
    }

    @Override
    public List<BookingResponseDto> findBookings(BookingStatus status, Long roomId, Long userId) {
        List<Booking> bookings;

        if (status != null && roomId != null && userId != null) {
            bookings = repository.findByStatusAndRoomIdAndUserId(status, roomId, userId);
        } else if (status != null && roomId != null) {
            bookings = repository.findByStatusAndRoomId(status, roomId);
        } else if (status != null && userId != null) {
            bookings = repository.findByStatusAndUserId(status, userId);
        } else if (roomId != null && userId != null) {
            bookings = repository.findByRoomIdAndUserId(roomId, userId);
        } else if (status != null) {
            bookings = repository.findByStatus(status);
        } else if (roomId != null) {
            bookings = repository.findByRoomId(roomId);
        } else if (userId != null) {
            bookings = repository.findByUserId(userId);
        } else {
            bookings = repository.findAll();
        }

        return bookings.stream()
                .map(BookingMapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }




    @Override
    public void changeBookingDates(Long id, UpdateBookingDatesDto updateBookingDatesDto) {
        Booking booking = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room not found with id: " + id)
        );

        if(!updateBookingDatesDto.getCheckOut().isAfter(updateBookingDatesDto.getCheckIn())){
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        booking.setCheckIn(updateBookingDatesDto.getCheckIn());
        booking.setCheckOut(updateBookingDatesDto.getCheckOut());
        repository.save(booking);

    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = findBookingById(id);
        if(booking.getStatus().equals(BookingStatus.PENDING)){
            throw new BadRequestException("Booking cannot be cancelled");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        repository.save(booking);
    }

    @Override
    public void approveBooking(Long id) {
        Booking booking = findBookingById(id);
        if(!booking.getStatus().equals(BookingStatus.PENDING)){
            throw new BadRequestException("Booking cannot be approved");
        }
        booking.setStatus(BookingStatus.CONFIRMED);
        repository.save(booking);
    }

    @Override
    public void rejectBooking(Long id) {
        Booking booking = findBookingById(id);
        if(!booking.getStatus().equals(BookingStatus.PENDING)){
            throw new BadRequestException("Booking cannot be rejected");
        }
        booking.setStatus(BookingStatus.REJECTED);
        repository.save(booking);
    }

    @Override
    public void checkInBooking(Long id) {
        Booking booking = findBookingById(id);
        if(!booking.getStatus().equals(BookingStatus.CONFIRMED)){
            throw new BadRequestException("Booking cannot be checked in");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        repository.save(booking);
    }

    @Override
    public void checkOutBooking(Long id) {
        Booking booking = findBookingById(id);
        if(!booking.getStatus().equals(BookingStatus.CHECKED_IN)){
            throw new BadRequestException("Booking cannot be checked out");
        }
        booking.setStatus(BookingStatus.CHECKED_OUT);
        repository.save(booking);
    }

    private Booking findBookingById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Booking not found with id: " + id)
        );
    }

}
