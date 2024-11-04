package com.tmkproperties.booking.controller;

import com.tmkproperties.booking.config.kafka.KafkaMessageSender;
import com.tmkproperties.booking.dto.*;
import com.tmkproperties.booking.exception.ResourceNotFoundException;
import com.tmkproperties.booking.service.IBookingService;
import com.tmkproperties.booking.service.client.ReceiptFiegnClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bookings", produces = "application/json")
@Validated
@RequiredArgsConstructor
@Tag(name = "Bookings REST API",
        description = "Endpoints for managing bookings, including creating, retrieving, updating booking dates, and performing actions such as approving, rejecting, checking in, checking out, and cancelling bookings."
)
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final IBookingService service;
    private final ReceiptFiegnClient receiptFiegnClient;


    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    @Operation(
            summary = "Add new booking ",
            description = "Add a reservation based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping(value = "/user", produces = "application/json")
    public ResponseEntity<BookingResponseDtoForUser> createBooking(
            @Valid @RequestBody BookingRequestDto bookingRequestDto, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        BookingResponseDtoForUser booking = service.createBooking(bookingRequestDto, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @Operation(
            summary = "Get all Bookings of a user",
            description = "Get all Bookings of a user based on the provided email."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/user")
    public ResponseEntity<List<BookingResponseDtoForUser>> getAllUserBookings(@AuthenticationPrincipal Jwt principal){
        String userEmail = principal.getClaimAsString("sub");
        List<BookingResponseDtoForUser> bookings = service.getAllBookingsByUser(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @Operation(
            summary = "Get a booking of a user",
            description = "Get a booking of a user based on the provided booking id and email."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/user/{id}")
    public ResponseEntity<BookingResponseDtoForUser> getUserBooking(@PathVariable Long id,@AuthenticationPrincipal Jwt principal){
        String email = principal.getClaimAsString("sub");
        BookingResponseDtoForUser bookings = service.getUserBooking(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @Operation(
            summary = "Get all Bookings of a host",
            description = "Get all Bookings of a host based on the provided hotel email."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/host")
    public ResponseEntity<List<BookingResponseDtoForHost>> getAllHostBookings(@AuthenticationPrincipal Jwt principal){
        String email = principal.getClaimAsString("sub");
        List<BookingResponseDtoForHost> bookings = service.getAllHostBookings(email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }


    @Operation(
            summary = "Get a booking of a host",
            description = "Get a booking of a host based on the provided booking id and email."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/host/{id}")
    public ResponseEntity<BookingResponseDtoForHost> getHostBooking(@PathVariable Long id,@AuthenticationPrincipal Jwt principal){
        logger.error("Host email: {}", principal.getClaimAsString("sub"));
        String email = principal.getClaimAsString("sub");
        BookingResponseDtoForHost bookings = service.getHostBooking(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }


    @Operation(
            summary = "Update Booking Dates",
            description = "REST API to update booking dates based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseDto> changeBookingDates(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt principal,
            @RequestBody UpdateBookingDatesDto updateBookingDatesDto)
    {
        String email = principal.getClaimAsString("sub");
        logger.error("Booking dates changed for user: {}, booking id: {}", email, id);
        service.changeBookingDates(id, updateBookingDatesDto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking dates changed successfully."));
    }

    @Operation(
            summary = "Cancel Booking",
            description = "REST API to cancel a booking based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/user/{id}/cancel")
    public ResponseEntity<ResponseDto> cancelBooking(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.cancelBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking canceled successfully."));
    }

    @Operation(
            summary = "Approve Booking",
            description = "REST API to approve a booking based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/host/{id}/approve")
    public ResponseEntity<ResponseDto> approveBooking(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        BookingResponseDtoForUser booking = service.approveBooking(id, email);

        kafkaMessageSender.sendMessage("booking", 0, booking.getId().toString(), booking);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking approved successfully."));
    }

    @Operation(
            summary = "Reject Booking",
            description = "REST API to reject a booking based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/host/{id}/reject")
    public ResponseEntity<ResponseDto> rejectBooking(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.rejectBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking rejected successfully."));
    }


    @Operation(
            summary = "Check In Booking",
            description = "REST API to check in a booking based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/host/{id}/checkin")
    public ResponseEntity<ResponseDto> checkInBooking(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.checkInBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking checked in successfully."));
    }


    @Operation(
            summary = "Check Out Booking",
            description = "REST API to check out a booking based on the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/host/{id}/checkout")
    public ResponseEntity<ResponseDto> checkOutBooking(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.checkOutBooking(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Booking checked out successfully."));
    }

    @GetMapping("/receipt/{bookingId}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long bookingId, @AuthenticationPrincipal Jwt principal) {
        BookingResponseDtoForHost booking = null;
        BookingResponseDtoForUser bookingForUser = null;

        try {
            booking = service.getHostBooking(bookingId, principal.getClaimAsString("sub"));
        } catch (Exception e) {
            bookingForUser = service.getUserBooking(bookingId, principal.getClaimAsString("sub"));
        }

        if (booking == null && bookingForUser == null) {
            throw new ResourceNotFoundException("No booking found for the requested user");
        }

        String fileName = (booking != null ? booking.getId() : bookingForUser.getId()) + ".pdf";
        return receiptFiegnClient.downloadPdf(fileName);
    }



}
