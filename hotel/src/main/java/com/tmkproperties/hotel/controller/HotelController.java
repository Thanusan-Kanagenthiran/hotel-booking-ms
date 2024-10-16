package com.tmkproperties.hotel.controller;

import com.tmkproperties.hotel.dto.HotelRequestDto;
import com.tmkproperties.hotel.dto.HotelResponseDto;
import com.tmkproperties.hotel.dto.ResponseDto;
import com.tmkproperties.hotel.service.IHotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@Validated
@RequiredArgsConstructor
public class HotelController {

    private final IHotelService service;

    @PostMapping
    public ResponseEntity<ResponseDto> createHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto) {

        service.createHotel(hotelRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, "Hotel created successfully."));
    }

    @GetMapping
    public ResponseEntity<List<HotelResponseDto>> findAll() {
        List<HotelResponseDto> hotels = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> findById(@PathVariable Long id) {
        HotelResponseDto hotel = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto, @PathVariable Long id) {
        service.updateHotel(id, hotelRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record updated successfully."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteHotel(@PathVariable Long id) {
        service.deleteHotel(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK, "Record deleted successfully."));
    }
}
