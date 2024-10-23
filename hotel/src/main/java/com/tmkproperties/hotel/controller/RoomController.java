package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.*;
import com.tmkproperties.hotel.service.implementation.RoomServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Validated
@RequiredArgsConstructor
@Tag(name = "Room REST API",
        description = "Endpoints for managing rooms."
)
public class RoomController {

    private final RoomServiceImpl service;


    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> findAll() {
        List<RoomResponseDto> rooms = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDtoWithDetails> findById(@PathVariable Long id) {
        RoomResponseDtoWithDetails room = service.findByIdWithDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }

    @PostMapping("/host")
    public ResponseEntity<ResponseDto> createRoom(@Valid @RequestBody RoomRequestDto roomRequestDto, @RequestParam String email) {
        service.createRoom(roomRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Room created successfully."));
    }

    @GetMapping("host/{id}")
    public ResponseEntity<RoomResponseDtoWithBookings> findByIdWithBookings(@PathVariable Long id, @RequestParam String email) {
        RoomResponseDtoWithBookings room = service.findByIdWithBookings(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }

    @PutMapping("/host/{id}")
    public ResponseEntity<ResponseDto> updateRoom(@Valid @RequestBody RoomRequestDto roomRequestDto, @PathVariable Long id, @RequestParam String email) {
        service.updateRoom(id, roomRequestDto, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record updated successfully."));
    }

    @DeleteMapping("/host/{id}")
    public ResponseEntity<ResponseDto> deleteRoom(@PathVariable Long id, @RequestParam String email) {
        service.deleteRoom(id, email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record deleted successfully."));
    }

}
