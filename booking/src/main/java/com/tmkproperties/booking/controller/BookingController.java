package com.tmkproperties.booking.controller;

import com.tmkproperties.booking.constants.BookingStatus;
import com.tmkproperties.booking.dto.BookingRequestDto;
import com.tmkproperties.booking.dto.BookingResponseDto;
import com.tmkproperties.booking.dto.ResponseDto;
import com.tmkproperties.booking.service.IBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@Validated
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createRoom(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        service.createBooking(bookingRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Booking created successfully" ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> findById(@PathVariable Long id) {
        BookingResponseDto booking = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(booking);
    }


    @PutMapping("/{id}/change-booking-dates")
    public ResponseEntity<ResponseDto> changeBookingDates(
            @PathVariable Long id,
            @RequestBody BookingRequestDto bookingRequestDto)
    {
        service.changeBookingDates(id, bookingRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking dates changed successfully."));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> findAll() {
        List<BookingResponseDto> bookings = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingResponseDto>> findAllByStatus(@PathVariable String status) {
        List<BookingResponseDto> bookings = service.findAllByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponseDto>> findAllByRoomId(@PathVariable Long roomId) {
        List<BookingResponseDto> bookings = service.findAllByRoomId(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> findAllByUserId(@PathVariable Long userId) {
        List<BookingResponseDto> bookings = service.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @PostMapping("/{id}/cancel-booking")
    public ResponseEntity<ResponseDto> cancelBooking(@PathVariable Long id) {
        service.cancelBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking canceled successfully" ));
    }

    @PostMapping("/{id}/approve-booking")
    public ResponseEntity<ResponseDto> confirmBooking(@PathVariable Long id) {
        service.approveBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking confirmed successfully" ));
    }

    @PostMapping("/{id}/reject-booking")
    public ResponseEntity<ResponseDto> rejectBooking(@PathVariable Long id) {
        service.rejectBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking rejected successfully" ));
    }

    @PostMapping("/{id}/check-in-booking")
    public ResponseEntity<ResponseDto> checkInBooking(@PathVariable Long id) {
        service.checkInBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking checked-in successfully" ));
    }

    @PostMapping("/{id}/check-out-booking")
    public ResponseEntity<ResponseDto> checkOutBooking(@PathVariable Long id) {
        service.checkOutBooking(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking checked-out successfully" ));
    }



}
