package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.*;
import com.tmkproperties.hotel.service.implementation.RoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Validated
@RequiredArgsConstructor
@Tag(name = "Room REST API",
        description = "Endpoints for managing rooms."
)
@Slf4j
public class RoomController {

    private final RoomServiceImpl service;


    @Operation(
            summary = "Get all rooms",
            description = "Get all rooms. "
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
    })
    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> findAll() {
        List<RoomResponseDto> rooms = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }

    @Operation(
            summary = "Get a room by id",
            description = "Get a room by id. "
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
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDtoWithDetails> findById(@PathVariable Long id) {
        RoomResponseDtoWithDetails room = service.findByIdWithDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }

    @Operation(
            summary = "Get all rooms for host",
            description = "Get all rooms for host. "
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
    })
    @GetMapping("/host")
    public ResponseEntity<List<RoomResponseDto>> findAllRoomsForHost(@AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        List<RoomResponseDto> rooms = service.findAllWithHost(email);
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }

    @Operation(
            summary = "Add new room",
            description = "Add a room based on the provided details. "
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
    })
    @PostMapping("/host")
    public ResponseEntity<ResponseDto> createRoom(@Valid @RequestBody RoomRequestDto roomRequestDto, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.createRoom(roomRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Room created successfully."));
    }


    @Operation(
            summary = "Get a room by id and host email",
            description = "Get a room by id and host email. "
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
    })
    @GetMapping("host/{id}")
    public ResponseEntity<RoomResponseDto> findByIdWithBookings(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        RoomResponseDto room = service.findByIdWithBookings(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }


    @Operation(
            summary = "Update a room",
            description = "Update a room based on the provided details. "
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
    })
    @PutMapping("/host/{id}")
    public ResponseEntity<ResponseDto> updateRoom(@Valid @RequestBody RoomRequestDto roomRequestDto, @PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.updateRoom(id, roomRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record updated successfully."));
    }


    @Operation(
            summary = "Delete a room",
            description = "Delete a room. "
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
    ))})
    @DeleteMapping("/host/{id}")
    public ResponseEntity<ResponseDto> deleteRoom(@PathVariable Long id, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getClaimAsString("sub");
        service.deleteRoom(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record deleted successfully."));
    }

}
