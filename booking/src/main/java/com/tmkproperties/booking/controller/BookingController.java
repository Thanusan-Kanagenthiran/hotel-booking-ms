package com.tmkproperties.booking.controller;

import com.tmkproperties.booking.config.kafka.KafkaMessageSender;
import com.tmkproperties.booking.dto.*;
import com.tmkproperties.booking.service.IBookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bookings", produces = "application/json")
@Validated
@RequiredArgsConstructor
@Tag(name = "Bookings REST API",
        description = "Endpoints for managing bookings, including creating, retrieving, updating booking dates, and performing actions such as approving, rejecting, checking in, checking out, and cancelling bookings. You can filter bookings using query parameters like status, room ID, and user ID."
)
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final IBookingService service;


    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> createBooking(
            @Valid @RequestBody BookingRequestDto bookingRequestDto,
            @RequestParam String email) {
        BookingResponseDtoForUser booking = service.createBooking(bookingRequestDto, email);

        try {
            kafkaMessageSender.sendMessage("booking", 0, booking.getId().toString(), booking);
        } catch (Exception e) {
            logger.error("Failed to send message to Kafka: {}", e.getMessage(), e);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Booking created successfully"));
    }


    @GetMapping("/user")
    public ResponseEntity<List<BookingResponseDtoForUser>> getAllUserBookings(@RequestParam String email){
        List<BookingResponseDtoForUser> bookings = service.getAllBookingsByUser(email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BookingResponseDtoForUser> getUserBooking(@PathVariable Long id,@RequestParam String email){
        BookingResponseDtoForUser bookings = service.getUserBooking(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/host")
    public ResponseEntity<List<BookingResponseDtoForHost>> getAllHostBookings(@RequestParam String email){
        List<BookingResponseDtoForHost> bookings = service.getAllHostBookings(email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<BookingResponseDtoForHost> getHostBooking(@PathVariable Long id,@RequestParam String email){
        BookingResponseDtoForHost bookings = service.getHostBooking(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseDto> changeBookingDates(
            @PathVariable Long id,
            @RequestParam String email,
            @RequestBody UpdateBookingDatesDto updateBookingDatesDto)
    {
        service.changeBookingDates(id, updateBookingDatesDto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking dates changed successfully."));
    }

    @PostMapping("/user/{id}/cancel")
    public ResponseEntity<ResponseDto> cancelBooking(@PathVariable Long id, @RequestParam String email) {
        service.cancelBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking canceled successfully."));
    }

    @PostMapping("/host/{id}/approve")
    public ResponseEntity<ResponseDto> approveBooking(@PathVariable Long id, @RequestParam String email) {
        service.approveBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking approved successfully."));
    }

    @PostMapping("/host/{id}/reject")
    public ResponseEntity<ResponseDto> rejectBooking(@PathVariable Long id, @RequestParam String email) {
        service.rejectBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking rejected successfully."));
    }

    @PostMapping("/host/{id}/checkin")
    public ResponseEntity<ResponseDto> checkInBooking(@PathVariable Long id, @RequestParam String email) {
        service.checkInBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking checked in successfully."));
    }

    @PostMapping("/host/{id}/checkout")
    public ResponseEntity<ResponseDto> checkOutBooking(@PathVariable Long id, @RequestParam String email) {
        service.checkOutBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking checked out successfully."));
    }


}
