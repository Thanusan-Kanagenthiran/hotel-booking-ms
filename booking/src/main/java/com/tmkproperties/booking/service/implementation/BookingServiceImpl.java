package com.tmkproperties.booking.service.implementation;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.dto.*;
import com.tmkproperties.booking.entity.Booking;
import com.tmkproperties.booking.exception.BadRequestException;
import com.tmkproperties.booking.exception.ResourceNotFoundException;
import com.tmkproperties.booking.mapper.BookingMapper;
import com.tmkproperties.booking.repository.BookingRepository;
import com.tmkproperties.booking.service.IBookingService;

import com.tmkproperties.booking.service.client.RoomFiegnClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository repository;
    private final RoomFiegnClient roomFeignClient;


    @Override
    public BookingResponseDtoForUser createBooking(BookingRequestDto bookingRequestDto, String email) {
        validateEmail(bookingRequestDto.getEmail(), email);

        int numberOfDays = validateCheckInOutDates(bookingRequestDto.getCheckIn(), bookingRequestDto.getCheckOut());
        checkRoomAvailability(bookingRequestDto.getRoomId(), bookingRequestDto.getCheckIn(), bookingRequestDto.getCheckOut());

        RoomResponseDtoWithDetails roomResponse = fetchRoomDetails(bookingRequestDto.getRoomId());
        BigDecimal amount = calculateBookingAmount(roomResponse.getPricePerNight(), numberOfDays);
        String hotelEmail = roomResponse.getEmail();
        Booking booking = BookingMapper.toBooking(bookingRequestDto);
        booking.setAmount(amount);
        booking.setHotelEmail(hotelEmail);
        Booking newBooking = repository.save(booking);

        RoomResponseDtoWithDetails roomDetails = fetchRoomDetails(newBooking.getRoomId());
        return BookingMapper.toBookingResponseDtoForUser(newBooking, roomDetails);

    }


    @Override
    public void changeBookingDates(Long id, UpdateBookingDatesDto updateBookingDatesDto, String email) {

        Booking booking = repository.findByIdAndGuestEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for ID: " + id + " and email: " + email));

        if (!booking.getStatus().equals(BookingStatus.PENDING)) {
            throw new BadRequestException("Booking can be updated only in pending state");
        }

        int numberOfDays = validateCheckInOutDates(updateBookingDatesDto.getCheckIn(), updateBookingDatesDto.getCheckOut());
        checkRoomAvailabilityForUpdate(id, updateBookingDatesDto.getCheckIn(), updateBookingDatesDto.getCheckOut(),booking.getId());

        RoomResponseDtoWithDetails roomResponse = fetchRoomDetails(id);
        BigDecimal amount = calculateBookingAmount(roomResponse.getPricePerNight(), numberOfDays);

        booking.setCheckIn(updateBookingDatesDto.getCheckIn());
        booking.setCheckOut(updateBookingDatesDto.getCheckOut());
        booking.setAmount(amount);

        repository.save(booking);
    }

    @Override
    public void cancelBooking(Long id, String email) {
        Optional<Booking> booking = repository.findByIdAndGuestEmail(id, email);

        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found");
        }

        if (!booking.get().getStatus().equals(BookingStatus.PENDING)) {
            throw new BadRequestException("Booking already cancelled");
        }

        repository.updateBookingStatus(id, BookingStatus.CANCELLED);
    }

    @Override
    public BookingResponseDtoForUser approveBooking(Long id, String email) {
        Booking booking = repository.findByIdAndHotelEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getStatus().equals(BookingStatus.PENDING)) {
            throw new BadRequestException("Booking cannot be approved as it is already " + booking.getStatus());
        }

        repository.updateBookingStatus(id, BookingStatus.CONFIRMED);
        RoomResponseDtoWithDetails roomDetails = fetchRoomDetails(booking.getRoomId());
        return BookingMapper.toBookingResponseDtoForUser(booking, roomDetails);
    }


    @Override
    public void rejectBooking(Long id, String email) {

        Optional<Booking> booking = repository.findByIdAndHotelEmail(id, email);
        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found");
        }

        if (!booking.get().getStatus().equals(BookingStatus.PENDING)) {
            throw new BadRequestException("Booking already rejected");
        }

        repository.updateBookingStatus(id, BookingStatus.REJECTED);

    }

    @Override
    public void checkInBooking(Long id, String email) {
        Optional<Booking> booking = repository.findByIdAndHotelEmail(id, email);
        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found");
        }
        if (!booking.get().getStatus().equals(BookingStatus.CONFIRMED)) {
            throw new BadRequestException("Booking already rejected");
        }

        repository.updateBookingStatus(id, BookingStatus.CHECKED_IN);
    }

    @Override
    public void checkOutBooking(Long id, String email) {
        Optional<Booking> booking = repository.findByIdAndHotelEmail(id, email);
        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found");
        }

        if (!booking.get().getStatus().equals(BookingStatus.CHECKED_IN)) {
            throw new BadRequestException("Booking already rejected");
        }

        repository.updateBookingStatus(id, BookingStatus.CHECKED_OUT);
    }

    @Override
    public List<BookingResponseDtoForUser> getAllBookingsByUser(String email) {
        List<Booking> bookings = repository.findByGuestEmail(email);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for the user");
        }

        return bookings.stream()
                .map(booking -> {
                    RoomResponseDtoWithDetails roomDetails = fetchRoomDetails(booking.getRoomId());
                    return BookingMapper.toBookingResponseDtoForUser(booking, roomDetails);
                }).collect(Collectors.toList());
    }


    @Override
    public BookingResponseDtoForUser getUserBooking(Long id, String email) {
        Optional<Booking> booking = repository.findByIdAndGuestEmail(id, email);
        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found");
        }
        RoomResponseDtoWithDetails roomDetails = fetchRoomDetails(booking.get().getRoomId());
       return BookingMapper.toBookingResponseDtoForUser(booking.get(), roomDetails);

    }

    @Override
    public List<BookingResponseDtoForHost> getAllHostBookings(String email) {
        List<Booking> bookings = repository.findByHotelEmail(email);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for the user");
        }

        return bookings.stream()
                .map(booking -> {
                    RoomResponseDtoWithDetails roomDetails = fetchRoomDetails(booking.getRoomId());
                    return BookingMapper.toBookingResponseDtoForHost(booking, roomDetails.getId());
                }).collect(Collectors.toList());
    }

    @Override
    public BookingResponseDtoForHost getHostBooking(Long id, String email) {
        Optional<Booking> booking = repository.findByIdAndHotelEmail(id, email);
        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found");
        }
        RoomResponseDtoWithDetails roomDetails = fetchRoomDetails(booking.get().getRoomId());
        return BookingMapper.toBookingResponseDtoForHost(booking.get(), roomDetails.getId());
    }

    private void validateEmail(String bookingEmail, String accountEmail) {
        if (!bookingEmail.equals(accountEmail)) {
            throw new ResourceNotFoundException("Email mismatch. Please provide your account email.");
        }
    }

    private int validateCheckInOutDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkOut.isBefore(checkIn)) {
            throw new BadRequestException("Check out date cannot be before check in date");
        }
        return (int) (checkOut.toEpochDay() - checkIn.toEpochDay());
    }

    public void checkRoomAvailability(Long id, LocalDate checkin, LocalDate checkout) {
        List<Booking> existingBookings = repository.findByRoomId(id);

        for (Booking booking : existingBookings) {
            if (!booking.getStatus().equals(BookingStatus.REJECTED)) {
                if (checkin.isBefore(booking.getCheckOut()) &&
                        checkout.isAfter(booking.getCheckIn())) {
                    throw new ResourceNotFoundException("Room is already booked for the selected dates");
                }
            }
        }
    }

    public void checkRoomAvailabilityForUpdate(Long roomId, LocalDate checkin, LocalDate checkout, Long currentBookingId) {
        List<Booking> existingBookings = repository.findByRoomId(roomId);

        for (Booking booking : existingBookings) {
            if (booking.getId().equals(currentBookingId)) {
                continue;
            }

            if (!booking.getStatus().equals(BookingStatus.REJECTED)) {
                if (checkin.isBefore(booking.getCheckOut()) &&
                        checkout.isAfter(booking.getCheckIn())) {
                    throw new ResourceNotFoundException("Room is already booked for the selected dates");
                }
            }
        }
    }


    private RoomResponseDtoWithDetails fetchRoomDetails(Long roomId) {
        ResponseEntity<RoomResponseDtoWithDetails> responseEntity = roomFeignClient.findById(roomId);

        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            throw new ResourceNotFoundException("Room not found or unavailable");
        }

        return responseEntity.getBody();
    }


    private BigDecimal calculateBookingAmount(BigDecimal pricePerNight, int numberOfDays) {
        return pricePerNight.multiply(BigDecimal.valueOf(numberOfDays));
    }
}
