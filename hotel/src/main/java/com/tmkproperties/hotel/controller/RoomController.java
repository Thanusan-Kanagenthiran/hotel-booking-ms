package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.RoomRequestDto;
import com.tmkproperties.hotel.dto.RoomResponseDto;
import com.tmkproperties.hotel.dto.ResponseDto;
import com.tmkproperties.hotel.service.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Validated
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createRoom(@Valid @RequestBody RoomRequestDto roomRequestDto) {
        service.createRoom(roomRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Room created successfully."));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> findAll() {
        List<RoomResponseDto> rooms = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> findById(@PathVariable Long id) {
        RoomResponseDto room = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(room);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateRoom(@Valid @RequestBody RoomRequestDto roomRequestDto, @PathVariable Long id) {
        service.updateRoom(id, roomRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRoom(@PathVariable Long id) {
        service.deleteRoom(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record deleted successfully."));
    }
}
